package com.http.post.exceptions;

/**
 * Exception thrown when the method is not supported
 */
public class InvalidMethodException extends Throwable {
    public InvalidMethodException(String method) {
        super("Invalid method: " + method);
    }
}
