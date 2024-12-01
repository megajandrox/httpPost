package orm;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class ResultSetUtils {

    public static <T> void addPersistedResult(Class<T> type, T entity, PreparedStatement ps) throws SQLException, NoSuchFieldException, IllegalAccessException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                Field idField = getFieldFromHierarchy(type, "id");
                idField.setAccessible(true);
                idField.set(entity, rs.getLong(1));
            }
        }
    }
}
