package com.http.post.utils.exceptions;

import java.sql.SQLException;

public abstract class DBRuntimeRuntimeException extends RuntimeException {

    public DBRuntimeRuntimeException(String msg) { super(msg); }

    public DBRuntimeRuntimeException(SQLException e) {
        super(e);
    }

    public DBRuntimeRuntimeException(ClassNotFoundException e) {
        super(e);
    }

    public DBRuntimeRuntimeException(String s, SQLActionException e) {
        super(s, e);
    }
}
