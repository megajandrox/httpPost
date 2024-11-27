package orm;

import com.http.post.utils.bussiness.exceptions.CreateException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> extends BaseORM {

    public GenericDao(Class type) {
        super(type);
    }

    public void create(T entity, Connection conn) throws CreateException {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = type.getSimpleName().toLowerCase();
        sql.append(tableName).append(" (");
        List<String> fields = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            if (!field.getName().equalsIgnoreCase("id") &&
                    field.getAnnotation(OneToOne.class) == null &&
                    field.getAnnotation(OneToMany.class) == null) {
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

        try (PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                if (value != null && value.getClass().isEnum()) {
                    ps.setObject(i + 1, value.toString());
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
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new CreateException(e.getMessage());
        }
    }
}
