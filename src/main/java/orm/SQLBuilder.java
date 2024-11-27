package orm;

import java.util.ArrayList;
import java.util.List;

public class SQLBuilder {

    public static <T> String buildInsert(Class<T> type, T entity, List<Object> values) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = type.getSimpleName().toLowerCase();
        sql.append(tableName).append(" (");

        List<String> fields = new ArrayList<>();
        ReflexionUtils.addRegularFields(type, entity, fields, values);

        sql.append(String.join(", ", fields)).append(") VALUES (");
        sql.append("?,".repeat(fields.size()).replaceAll(",$", ""));
        sql.append(")");
        return sql.toString();
    }
}
