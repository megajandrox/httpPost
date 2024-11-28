package orm.handlers;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.CreateException;
import orm.GenericDao;
import orm.mapping.OneToOne;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class OneToOneField {

    public static synchronized <T> void handle(Connection conn, T entity, Class<?> type, BiConsumer<GenericDao, Entity> daoOperation)
            throws IllegalAccessException, SQLException, NoSuchFieldException {
            getFields(type)
                .forEach(f  -> callGenericDAO(conn, entity, type, f, daoOperation));
    }

    private static <T> void callGenericDAO(Connection conn, T entity, Class<?> type, Field field, BiConsumer<GenericDao, Entity> daoOperation) {
        field.setAccessible(true);
        try {
            Entity relatedEntity = (Entity) field.get(entity);
            if (relatedEntity != null) {
                Field idField = getFieldFromHierarchy(type, "id");
                idField.setAccessible(true);
                long parentId = (long) idField.get(entity);
                addForeignKeyField(field, relatedEntity, parentId);

                GenericDao relatedDao = new GenericDao(relatedEntity.getClass());
                daoOperation.accept(relatedDao, relatedEntity);  // Perform the operation (create or update)
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Field> getFields(Class<?> type) throws SQLException {
        return Arrays.stream(type.getDeclaredFields()).filter(f -> f.getAnnotation(OneToOne.class) != null).collect(Collectors.toList());
    }

    private static void addForeignKeyField(Field f, Entity relatedEntity, long parentId) throws NoSuchFieldException, IllegalAccessException {
        String mappedBy = f.getAnnotation(OneToOne.class).mappedBy();
        Field foreignKeyField = getFieldFromHierarchy(relatedEntity.getClass(), mappedBy);
        foreignKeyField.setAccessible(true);
        foreignKeyField.set(relatedEntity, parentId);
    }
}
