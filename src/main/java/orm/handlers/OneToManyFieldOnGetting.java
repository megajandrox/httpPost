package orm.handlers;

import orm.ColumnUtils;
import orm.mapping.OneToMany;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static orm.sql.SQLBuilder.getSelectFrom;

public class OneToManyFieldOnGetting {

    public static synchronized <T> void handle(ResultSet rs, Field field, T entity, Connection conn) throws SQLException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (field.isAnnotationPresent(OneToMany.class)) {
            OneToMany annotation = field.getAnnotation(OneToMany.class);
            Class<?> targetEntity = annotation.targetEntity();
            String foreignKey = ColumnUtils.toSnakeCase(annotation.mappedBy());
            String relatedSql = getSelectFrom(targetEntity, foreignKey);
            List<Object> relatedEntities = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(relatedSql)) {
                ps.setObject(1, rs.getObject(foreignKey));
                try (ResultSet relatedRs = ps.executeQuery()) {
                    while (relatedRs.next()) {
                        Object relatedEntity = targetEntity.getDeclaredConstructor().newInstance();
                        for (Field relatedField : targetEntity.getDeclaredFields()) {
                            relatedField.setAccessible(true);
                            relatedField.set(relatedEntity, relatedRs.getObject(relatedField.getName()));
                        }
                        relatedEntities.add(relatedEntity);
                    }
                }
            }
            field.set(entity, relatedEntities);
        }
    }
}
