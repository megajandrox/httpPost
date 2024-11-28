package orm.mappers;

import orm.handlers.EnumFieldOnGetting;
import orm.handlers.OneToManyFieldOnGetting;
import orm.handlers.OneToOneFieldOnGetting;
import orm.handlers.PrimitiveFieldOnGetting;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;

import static orm.ReflexionUtils.getAllFields;

public class MapResultToEntity {

    public static <T> T map(ResultSet rs, Class<T> type, Connection conn) throws Exception {
        T entity = type.getDeclaredConstructor().newInstance();
        for (Field field : getAllFields(type)) {
            field.setAccessible(true);
            OneToManyFieldOnGetting.handle(rs, field, entity, conn);
            OneToOneFieldOnGetting.handle(rs, field, entity);
            EnumFieldOnGetting.handle(rs, field, entity);
            PrimitiveFieldOnGetting.handle(rs, field, entity, type);
        }
        return entity;
    }
}
