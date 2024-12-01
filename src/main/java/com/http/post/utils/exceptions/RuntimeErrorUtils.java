package com.http.post.utils.exceptions;

public class RuntimeErrorUtils {

    public static void declareRollbackException() {
        String msgError = "There was an error trying to rollback the transaction";
        System.err.println(msgError);
        throw new RollbackSQLRuntimeException(msgError);
    }

    public static void declareDBConnectionException() {
        String msgError = "There was an error trying to close the connection";
        System.err.println(msgError);
        throw new DBConnectionRuntimeException(msgError);
    }
}
