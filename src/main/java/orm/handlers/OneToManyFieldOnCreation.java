package orm.handlers;

import java.sql.Connection;

public class OneToManyFieldOnCreation {

    public static synchronized <T> void handle(Connection conn, T entity, Class<?> type) throws Exception {
        OneToManyField.handle( entity, type, (dao, relatedEntity) -> {
            try {
                dao.create(relatedEntity, conn);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create related entity: " + e.getMessage(), e);
            }
        });
    }
}
