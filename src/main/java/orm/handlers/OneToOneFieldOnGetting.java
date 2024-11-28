package orm.handlers;

import orm.mapping.OneToOne;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class OneToOneFieldOnGetting {

    public static <T> void handle(ResultSet rs, Field field, T entity) throws Exception{
        if (field.isAnnotationPresent(OneToOne.class)) {
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
        }
    }
}
