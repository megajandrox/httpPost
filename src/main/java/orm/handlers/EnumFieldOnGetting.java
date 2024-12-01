package orm.handlers;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EnumFieldOnGetting {

    public static <T> void handle(ResultSet rs, Field field, T entity) throws Exception {
        if (field.getType().isEnum()) {
            String columnName = field.getName();
            String value = rs.getString(columnName);
            if (value != null) {
                @SuppressWarnings("unchecked")
                Enum<?> enumValue = Enum.valueOf((Class<Enum>) field.getType(), value.toUpperCase());
                field.set(entity, enumValue);
            }
        }
    }
}
