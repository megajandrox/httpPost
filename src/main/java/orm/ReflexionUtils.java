package orm;

import orm.mapping.OneToMany;
import orm.mapping.OneToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflexionUtils {

    public static <T> void addRegularFields(Class<T> type, T entity, List<String> fields, List<Object> values) {
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
    }

    public static Field getFieldFromHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // Move to superclass
            }
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in class hierarchy.");
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
