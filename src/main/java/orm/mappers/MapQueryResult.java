package orm.mappers;

import orm.handlers.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static orm.ReflexionUtils.getAllFields;

public class MapQueryResult {

    public static <T> List<T> addQueryResult(Class<T> type, PreparedStatement ps, Connection conn) throws Exception {
        try (ResultSet rs = ps.executeQuery()) {
            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapResultSetToEntity(type, rs, conn));
            }
            return result;
        }
    }

    public static <T> T mapResultSetToEntity(Class<T> type, ResultSet rs, Connection conn) throws Exception {
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
