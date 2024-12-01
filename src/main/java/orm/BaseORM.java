package orm;

import com.http.post.model.Entity;
import com.http.post.repository.DAO;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;
import orm.mappers.MapQueryResult;
import orm.operations.CreateOperation;
import orm.operations.UpdateOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class BaseORM<T extends Entity> implements DAO<T> {
    protected final Class<T> type;

    public BaseORM(Class<T> type) {
        this.type = type;
    }

    protected Connection getConnection() {
        return DBManager.connect();
    }

    public void create(T entity) throws CreateException {
        try {
            new CreateOperation<>(this.type).execute(entity);
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        }
    }

    public void update(T entity) throws UpdateException {
        new UpdateOperation<>(this.type).execute(entity);
    }

    public Optional<T> get(Long id) throws GetException {
        String sql = "SELECT * FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(MapQueryResult.mapResultSetToEntity(type, rs, conn));
                    }
                }
        } catch (Exception e) {
            throw new GetException(e.getMessage());
        }
        return Optional.empty();
    }

    public int delete(Long id) throws DeletionException {
        String sql = "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        Connection conn = getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int executed = ps.executeUpdate();
            conn.commit();
            return executed;
        } catch (SQLException e) {
            throw new DeletionException(e.getMessage());
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new DeletionException(e.getMessage());
            }
        }
    }

    public List<T> getAll() throws SearchException {
        String sql = "SELECT * FROM " + type.getSimpleName().toLowerCase();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return MapQueryResult.addQueryResult(type, ps, conn);
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
    }
}

