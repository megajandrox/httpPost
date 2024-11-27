package orm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PreparedStatementUtils {

    public static void addValuesOnPreparedStatement(List<Object> values, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value != null && value.getClass().isEnum()) {
                ps.setObject(i + 1, value.toString());
            } else {
                ps.setObject(i + 1, value);
            }
        }
    }
}
