package orm.handlers;

import java.lang.reflect.Field;
import java.sql.Connection;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class OneToManyFieldOnDeletion {

    public static synchronized <T> void handle(Connection conn, T entity, Class<?> type) throws Exception {
        OneToManyField.handle( entity, type, (dao, relatedEntity) -> {
            try {
                Field idField = getFieldFromHierarchy(entity.getClass(), "id");
                idField.setAccessible(true);
                long parentId = (long) idField.get(entity);
                dao.delete(parentId, conn);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to update related entity: " + e.getMessage(), e);
            }
        });
    }
}
