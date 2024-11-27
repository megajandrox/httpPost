package orm;

import com.http.post.model.Entity;
import com.http.post.repository.DAO2;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = type.getSimpleName().toLowerCase();
        sql.append(tableName).append(" (");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        // Collect fields and values for the main entity
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equalsIgnoreCase("id") && (field.getAnnotation(OneToOne.class) == null &&
                    field.getAnnotation(OneToMany.class) == null)) {
                fields.add(field.getName());
                try {
                    values.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        sql.append(String.join(", ", fields)).append(") VALUES (");
        sql.append("?,".repeat(fields.size()).replaceAll(",$", ""));
        sql.append(")");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the main entity
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);

                // Check if the value is an Enum and convert to String (or other appropriate type)
                if (value != null && value.getClass().isEnum()) {
                    ps.setObject(i + 1, value.toString()); // Store enum as String
                } else {
                    ps.setObject(i + 1, value);
                }
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Field idField = getFieldFromHierarchy(type, "id");
                    idField.setAccessible(true);
                    idField.set(entity, rs.getLong(1));
                }
            }

            // Handle @OneToOne relationships
            for (Field field : type.getDeclaredFields()) {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                if (oneToOne != null) {
                    handleOneToOne(conn, field, entity, oneToOne);
                }
            }
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

    protected Field getFieldFromHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Move to superclass
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy.");
    }

    protected void handleOneToOne(Connection conn, Field field, T entity, OneToOne oneToOne)
            throws IllegalAccessException, SQLException, NoSuchFieldException {
        field.setAccessible(true);
        Entity relatedEntity = (Entity) field.get(entity);
        if (relatedEntity != null) {
            Field idField = getFieldFromHierarchy(type, "id");
            idField.setAccessible(true);
            long parentId = (long) idField.get(entity);

            // Set the foreign key in the related entity
            String mappedBy = oneToOne.mappedBy();
            Field foreignKeyField = getFieldFromHierarchy(relatedEntity.getClass(), mappedBy);
            //Field foreignKeyField = relatedEntity.getClass().getDeclaredField(mappedBy);
            foreignKeyField.setAccessible(true);
            //TODO review this part.
            foreignKeyField.set(relatedEntity, parentId);

            // Persist the related entity
            GenericDao relatedDao = new GenericDao(relatedEntity.getClass());
            try {
                relatedDao.create(relatedEntity, conn);
            } catch (CreateException e) {
                System.err.println("Failed to create related entity: " + e.getMessage());
                throw new RuntimeException(e);
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

    protected T mapResultSetToEntity(ResultSet rs) throws Exception {
        T entity = type.getDeclaredConstructor().newInstance();
        for (Field field : type.getDeclaredFields()) {
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
            } else {
                // Handle other field types normally
                field.set(entity, rs.getObject(columnName));
            }
        }
        return entity;
    }
}

