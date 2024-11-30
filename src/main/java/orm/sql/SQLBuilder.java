package orm.sql;

import orm.ColumnUtils;
import orm.ReflexionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class SQLBuilder {

    public static <T> String buildInsert(Class<T> type, T entity, List<Object> values) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = type.getSimpleName().toLowerCase();
        sql.append(tableName).append(" (");

        List<String> fields = new ArrayList<>();
        ReflexionUtils.addRegularFields(type, entity, fields, values);

        sql.append(fields.stream().map(ColumnUtils::toSnakeCase).collect(Collectors.joining(", "))).append(") VALUES (");
        sql.append("?,".repeat(fields.size()).replaceAll(",$", ""));
        sql.append(")");
        return sql.toString();
    }

    public static <T> String buildUpdate(Class<T> type, T entity, List<Object> values, Predicate<Field> filter, String idFieldName) {
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(type.getSimpleName().toLowerCase()).append(" SET ");
        List<String> fields = new ArrayList<>();
        Arrays.stream(type.getDeclaredFields()).filter(filter).forEach(field -> {
            field.setAccessible(true);
            fields.add(ColumnUtils.toSnakeCase(field.getName()) + " = ?");
            try {
                values.add(field.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        sql.append(String.join(", ", fields)).append(format(" WHERE %s = ? ", ColumnUtils.toSnakeCase(idFieldName)));
        return sql.toString();
    }

    public static String getSelectFrom(Class<?> targetEntity, String foreignKey) {
        return "SELECT * FROM " + targetEntity.getSimpleName().toLowerCase() + " WHERE " + foreignKey + " = ?";
    }
}
