package orm;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.CreateException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class OneToOneHandler {

    public static synchronized <T> void handleOneToOne(Connection conn, T entity, Class<?> type)
            throws IllegalAccessException, SQLException, NoSuchFieldException {
            Arrays.stream(type.getDeclaredFields()).filter(f -> f.getAnnotation(OneToOne.class) != null)
                .forEach(f  -> {
                    f.setAccessible(true);
                    Entity relatedEntity = null;
                    try {
                        relatedEntity = (Entity) f.get(entity);
                        if (relatedEntity != null) {
                            Field idField = getFieldFromHierarchy(type, "id");
                            idField.setAccessible(true);
                            long parentId = (long) idField.get(entity);
                            addForeignKeyField(f, relatedEntity, parentId);
                            // Persist the related entity
                            GenericDao relatedDao = new GenericDao(relatedEntity.getClass());
                            try {
                                relatedDao.create(relatedEntity, conn);
                            } catch (CreateException e) {
                                System.err.println("Failed to create related entity: " + e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static void addForeignKeyField(Field f, Entity relatedEntity, long parentId) throws NoSuchFieldException, IllegalAccessException {
        String mappedBy = f.getAnnotation(OneToOne.class).mappedBy();
        Field foreignKeyField = getFieldFromHierarchy(relatedEntity.getClass(), mappedBy);
        foreignKeyField.setAccessible(true);
        foreignKeyField.set(relatedEntity, parentId);
    }
}
