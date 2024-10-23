package com.http.post.utils.exceptions;

import java.sql.SQLException;

public class SQLActionException extends Exception {

    public SQLActionException(String msgError, SQLException e) {    super(msgError, e); }

}
