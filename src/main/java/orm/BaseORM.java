package orm;

import com.http.post.model.Entity;
import com.http.post.repository.DAO2;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static orm.OneToOneHandler.handleOneToOne;
import static orm.PreparedStatementUtils.addValuesOnPreparedStatement;
import static orm.ReflexionUtils.getFieldFromHierarchy;
import static orm.ResultSetUtils.addResult;

public abstract class BaseORM<T extends Entity> implements DAO2<T> {
    protected final Class<T> type;
    private Connection conn;
    public BaseORM(Class<T> type) {
        this.type = type;
    }

    protected Connection getConnection() {
        return DBManager.connect();
    }

    public void create(T entity) throws CreateException {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildInsert(type, entity, values);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addValuesOnPreparedStatement(values, ps);
            ps.executeUpdate();
            addResult(type, entity, ps);
            handleOneToOne(conn, entity, type);
            conn.commit();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new CreateException(e.getMessage());
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new CreateException(e.getMessage());
            }
        }
    }

    public Optional<T> get(Long id) throws GetException {
        String sql = "SELECT * FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs));
                }
            }
        } catch (Exception e) {
            throw new GetException(e.getMessage());
        }
        return Optional.empty();
    }

    public void update(T entity) throws UpdateException {
        //TODO review this code
        Long id = entity.getId();
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(type.getSimpleName().toLowerCase()).append(" SET ");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equalsIgnoreCase("id")) {
                fields.add(field.getName() + " = ?");
                try {
                    values.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        sql.append(String.join(", ", fields)).append(" WHERE id = ?");
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }
            ps.setLong(values.size() + 1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
        }
    }

    public void delete(Long id) throws DeletionException {
        String sql = "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DeletionException(e.getMessage());
        }
    }

    public List<T> getAll() throws SearchException {
        String sql = "SELECT * FROM " + type.getSimpleName().toLowerCase();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapResultSetToEntity(rs));
                }
                return result;
            }
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
    }

    private List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass(); // Traverse the superclass hierarchy
        }
        return fields;
    }

    protected T mapResultSetToEntity(ResultSet rs) throws Exception {
        T entity = type.getDeclaredConstructor().newInstance();
        for (Field field : getAllFields(type)) {
            field.setAccessible(true);
            String columnName = field.getName();
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany annotation = field.getAnnotation(OneToMany.class);
                Class<?> targetEntity = annotation.targetEntity();
                String foreignKey = annotation.mappedBy();
                String relatedSql = "SELECT * FROM " + targetEntity.getSimpleName().toLowerCase() + " WHERE " + foreignKey + " = ?";
                List<Object> relatedEntities = new ArrayList<>();

                try (Connection conn = getConnection();
                     PreparedStatement ps = conn.prepareStatement(relatedSql)) {
                    ps.setObject(1, rs.getObject(foreignKey)); // Assuming "id" is the primary key in the main entity
                    try (ResultSet relatedRs = ps.executeQuery()) {
                        while (relatedRs.next()) {
                            Object relatedEntity = targetEntity.getDeclaredConstructor().newInstance();

                            for (Field relatedField : targetEntity.getDeclaredFields()) {
                                relatedField.setAccessible(true);
                                relatedField.set(relatedEntity, relatedRs.getObject(relatedField.getName()));
                            }

                            relatedEntities.add(relatedEntity);
                        }
                    }
                }
                field.set(entity, relatedEntities);
            } else if (field.isAnnotationPresent(OneToOne.class)) {
                OneToOne annotation = field.getAnnotation(OneToOne.class);
                Class<?> targetEntity = annotation.targetEntity();
                String[] columns = annotation.columns();
                Object relatedEntity = targetEntity.getDeclaredConstructor().newInstance();
                for (String column : columns) {
                    Field relatedField = targetEntity.getDeclaredField(column);
                    relatedField.setAccessible(true);
                    relatedField.set(relatedEntity, rs.getObject(column));
                }
                field.set(entity, relatedEntity);

            } else if (field.getType().isEnum()) {
                // Handle Enums
                String value = rs.getString(columnName);
                if (value != null) {
                    @SuppressWarnings("unchecked")
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), value.toUpperCase());
                    field.set(entity, enumValue);
                }
            }  else if(columnName.equalsIgnoreCase("id")) {
                // Handle primary key
                Field foreignKeyField = getFieldFromHierarchy(type, "id");
                foreignKeyField.setAccessible(true);
                foreignKeyField.set(entity, rs.getLong(columnName));
            } else {
                // Handle other field types normally
                field.set(entity, rs.getObject(columnName));
            }
        }
        return entity;
    }
}

