package orm;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.CreateException;
import com.http.post.utils.bussiness.exceptions.DeletionException;
import com.http.post.utils.bussiness.exceptions.UpdateException;
import orm.sql.SQLBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static orm.ReflexionUtils.getFieldFromHierarchy;

public class GenericDao<T extends Entity> extends BaseORM<T> {

    public GenericDao(Class<T> type) {
        super(type);
    }

    public void create(T entity, Connection conn) throws CreateException {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildInsert(type, entity, values);
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addValuesOnPreparedStatement(values, ps);
            ps.executeUpdate();
            ResultSetUtils.addPersistedResult(type, entity, ps);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new CreateException(e.getMessage());
        }
    }

    public int update(T entity, Connection conn) throws UpdateException {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildUpdate(type, entity, values,f-> !f.getName().equalsIgnoreCase("requestId"), "requestId");
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addValuesOnPreparedStatement(values, ps);
            Field idField = getFieldFromHierarchy(entity.getClass(), "requestId");
            idField.setAccessible(true);
            long parentId = (long) idField.get(entity);
            ps.setLong(values.size() + 1, parentId);
            System.out.println(ps);
            return ps.executeUpdate();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new UpdateException(e.getMessage());
        }
    }

    public int delete(Long id, Connection conn) throws DeletionException {
        String sql = "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE request_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DeletionException(e.getMessage());
        }
    }

    private static void addValuesOnPreparedStatement(List<Object> values, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < values.size(); i++) {
            Object value = values.get(i);
            if (value != null && value.getClass().isEnum()) {
                ps.setObject(i + 1, value.toString());
            } else {
                ps.setObject(i + 1, value);
            }
        }
    }
}
