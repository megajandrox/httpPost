package orm.operations;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.UpdateException;
import orm.PreparedStatementUtils;
import orm.handlers.OneToManyFieldOnDeletion;
import orm.handlers.OneToManyFieldOnUpdating;
import orm.handlers.OneToOneFieldOnUpdating;
import orm.mapping.OneToMany;
import orm.mapping.OneToOne;
import orm.sql.SQLBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UpdateOperation <T extends Entity> extends ORMOperation<T> {
    private final Class<T> type;

    public UpdateOperation(Class<T> type) {
        this.type = type;
    }

    @Override
    protected void doOperation(Connection conn, T entity) throws Exception {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildUpdate(
                type,
                entity,
                values,
                f -> !f.getName().equalsIgnoreCase("id") &&
                        f.getAnnotation(OneToOne.class) == null &&
                        f.getAnnotation(OneToMany.class) == null,
                "id"
        );
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            PreparedStatementUtils.addValuesOnPreparedStatement(values, ps);
            ps.setLong(values.size() + 1, entity.getId());
            ps.executeUpdate();
            executeFieldCommands(conn, entity, this.type);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }
    }

    protected void executeFieldCommands(Connection conn, T entity, Class<T> type) throws Exception {
        OneToOneFieldOnUpdating.handle(conn, entity, type);
        OneToManyFieldOnDeletion.handle(conn, entity, type);
        OneToManyFieldOnUpdating.handle(conn, entity, type);
    }
}

