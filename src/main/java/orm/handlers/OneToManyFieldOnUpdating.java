package orm.handlers;

import java.sql.Connection;

public class OneToManyFieldOnUpdating {

    public static synchronized <T> void handle(Connection conn, T entity, Class<?> type) throws Exception {
        OneToManyField.handle( entity, type, (dao, relatedEntity) -> {
            try {
                dao.create(relatedEntity, conn);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to update related entity: " + e.getMessage(), e);
            }
        });

    }
}
