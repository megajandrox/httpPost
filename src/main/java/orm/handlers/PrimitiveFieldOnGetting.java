package orm.handlers;

import orm.mapping.OneToMany;
import orm.mapping.OneToOne;

import java.lang.reflect.Field;
import java.sql.ResultSet;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class PrimitiveFieldOnGetting {

    public static <T> void handle(ResultSet rs, Field field, T entity, Class<?> type) throws Exception {
        String columnName = field.getName();
        if(columnName.equalsIgnoreCase("id")) {
            Field foreignKeyField = getFieldFromHierarchy(type, "id");
            foreignKeyField.setAccessible(true);
            foreignKeyField.set(entity, rs.getLong(columnName));
        } else if(!field.isAnnotationPresent(OneToMany.class) && !field.isAnnotationPresent(OneToOne.class) && !field.getType().isEnum()) {
            field.set(entity, rs.getObject(columnName));
        }
    }
}
