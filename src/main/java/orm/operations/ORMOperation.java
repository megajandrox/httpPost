package orm.operations;

import com.http.post.model.Entity;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.UpdateException;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class ORMOperation <T extends Entity> {

    protected abstract void executeFieldCommands(Connection conn, T entity, Class<T> type) throws Exception;
    public void execute(T entity) throws UpdateException {
        Connection conn = DBManager.connect();
        try {
            doOperation(conn, entity);
            conn.commit();
        } catch (Exception e) {
            throw new UpdateException("Operation failed: " + e.getMessage());
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new UpdateException(e.getMessage());
            }
        }
    }

    protected abstract void doOperation(Connection conn, T entity) throws Exception;
}
