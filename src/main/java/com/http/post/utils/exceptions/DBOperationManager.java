package com.http.post.utils.exceptions;

import java.sql.SQLException;
import java.util.Optional;

import static com.http.post.utils.exceptions.RuntimeErrorUtils.declareDBConnectionException;
import static com.http.post.utils.exceptions.RuntimeErrorUtils.declareRollbackException;

public class DBOperationManager<T> {

    private DBOperationManager() {
    }

    static DBOperationManager instance = new DBOperationManager();

    public static DBOperationManager getInstance() {
        return instance;
    }

    @FunctionalInterface
    public interface SqlAction<T> {
        Optional<T> run() throws SQLException;
    }

    @FunctionalInterface
    public interface DDLAction {
        void run() throws SQLException;
    }

    @FunctionalInterface
    public interface SqlRollback {
        void run() throws SQLException;
    }

    public Optional<T> trySqlAction(AutoCloseable resource, SqlAction action, SqlRollback rollback) throws SQLActionException {
        try {
            Optional<T> result = action.run();
            if (result.isPresent()) {
                return result;
            }
        } catch (SQLException e) {
            if (rollback != null) {
                try {
                    rollback.run();
                } catch (SQLException re) {
                    declareRollbackException();
                }
            }
            String msgError = "There was an error performing the action";
            System.err.println(msgError);
            throw new SQLActionException(msgError, e);
        } finally {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                declareDBConnectionException();
            }
        }
        return Optional.empty();
    }

    public Optional<T> tryDDLAction(AutoCloseable resource, DDLAction action, SqlRollback rollback) {
        try {
            action.run();
        } catch (SQLException e) {
            if (rollback != null) {
                try {
                    rollback.run();
                } catch (SQLException re) {
                    declareRollbackException();
                }
            }
            String msgError = "There was an error performing the DDL action";
            System.err.println(msgError);
            throw new DDLActionException(msgError);
        } finally {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                declareDBConnectionException();
            }
        }
        return Optional.empty();
    }
}
