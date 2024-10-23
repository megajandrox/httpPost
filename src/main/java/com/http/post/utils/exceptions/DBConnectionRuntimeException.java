package com.http.post.utils.exceptions;

import java.sql.SQLException;

public class DBConnectionRuntimeException extends DBRuntimeRuntimeException {
    public DBConnectionRuntimeException(SQLException e) {
        super(e);
    }

    public DBConnectionRuntimeException(String msg) {
        super(msg);
    }
}
