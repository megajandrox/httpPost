package orm.operations;

import com.http.post.model.Entity;
import orm.handlers.OneToManyFieldOnCreation;
import orm.handlers.OneToOneFieldOnCreation;
import orm.sql.SQLBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static orm.PreparedStatementUtils.addValuesOnPreparedStatement;
import static orm.ResultSetUtils.addPersistedResult;
public class CreateOperation<T extends Entity> extends ORMOperation<T> {
    private final Class<T> type;

    public CreateOperation(Class<T> type) {
        this.type = type;
    }

    @Override
    protected void doOperation(Connection conn, T entity) throws Exception {
        List<Object> values = new ArrayList<>();
        String sql = SQLBuilder.buildInsert(type, entity, values);

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            addValuesOnPreparedStatement(values, ps);
            ps.executeUpdate();
            addPersistedResult(type, entity, ps);
            executeFieldCommands(conn, entity, this.type);
        }
    }

    protected void executeFieldCommands(Connection conn, T entity, Class<T> type) throws Exception {
        OneToOneFieldOnCreation.handle(conn, entity, type);
        OneToManyFieldOnCreation.handle(conn, entity, type);
    }
}

