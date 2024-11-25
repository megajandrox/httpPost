package orm;

import com.http.post.model.Body;
import com.http.post.model.Entity;
import com.http.post.repository.DAO;
import com.http.post.repository.DAO2;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseORM<T extends Entity> implements DAO2<T> {
    private final Class<T> type;

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

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equalsIgnoreCase("id")) {
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

            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Field idField = type.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(entity, rs.getInt(1));
                }
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new CreateException(e.getMessage());
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
            if (field.getType().equals(Body.class)) {
                // Create a Body object from the ResultSet
                Body body = new Body(
                        rs.getString("content"), // Body content
                        rs.getString("contenttype")     // Body type
                );
                field.set(entity, body);
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

