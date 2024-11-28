package orm;

import com.http.post.model.Entity;
import com.http.post.repository.DAO2;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;
import orm.handlers.OneToOneFieldOnCreation;
import orm.handlers.OneToOneFieldOnUpdating;
import orm.mappers.MapQueryResult;
import orm.mapping.OneToMany;
import orm.mapping.OneToOne;
import orm.sql.SQLBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static orm.PreparedStatementUtils.addValuesOnPreparedStatement;
import static orm.ResultSetUtils.addPersistedResult;

public abstract class BaseORM<T extends Entity> implements DAO2<T> {
    protected final Class<T> type;
    private Connection conn;
    public BaseORM(Class<T> type) {
        this.type = type;
    }

    protected Connection getConnection() {
        return DBManager.connect();
    }

    public void create(T entity) throws CreateException {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildInsert(type, entity, values);
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addValuesOnPreparedStatement(values, ps);
            ps.executeUpdate();
            addPersistedResult(type, entity, ps);
            OneToOneFieldOnCreation.handle(conn, entity, type);
            //TODO create one to many fields
            conn.commit();
        } catch (Exception e) {
            throw new CreateException(e.getMessage());
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new CreateException(e.getMessage());
            }
        }
    }

    public void update(T entity) throws UpdateException {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildUpdate(type, entity, values,  f-> !f.getName().equalsIgnoreCase("id") &&
                f.getAnnotation(OneToOne.class) == null &&
                f.getAnnotation(OneToMany.class) == null, "id");
        Long id = entity.getId();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            addValuesOnPreparedStatement(values, ps);
            ps.setLong(values.size() + 1, id);
            ps.executeUpdate();
            OneToOneFieldOnUpdating.handle(conn, entity, type);
            //TODO update one to many fields
            conn.commit();
        }  catch (Exception e) {
            throw new UpdateException(e.getMessage());
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                throw new UpdateException(e.getMessage());
            }
        }
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

    public void delete(Long id) throws DeletionException {
        String sql = "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DeletionException(e.getMessage());
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

