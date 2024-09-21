package com.http.post.exceptions;

public class InvalidMethodException extends Throwable {
    public InvalidMethodException(String method) {
        super("Invalid method: " + method);
    }
}
