package orm;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.CreateException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            ResultSetUtils.addResult(type, entity, ps);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            throw new CreateException(e.getMessage());
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
