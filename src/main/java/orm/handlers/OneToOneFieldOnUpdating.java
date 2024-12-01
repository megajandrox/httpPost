package orm.handlers;

import java.sql.Connection;

public class OneToOneFieldOnUpdating {

    public static synchronized <T> void handle(Connection conn, T entity, Class<?> type) throws Exception {
        OneToOneField.handle(conn, entity, type, (dao, relatedEntity) -> {
            try {
                dao.update(relatedEntity, conn);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create related entity: " + e.getMessage(), e);
            }
        });
    }
}
