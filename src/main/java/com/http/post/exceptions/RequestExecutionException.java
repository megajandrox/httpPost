package com.http.post.exceptions;

import java.io.IOException;

/**
 * Exception thrown when there is an error in the request
 */
public class RequestExecutionException extends Throwable {

    public RequestExecutionException(IOException e) {
        super("Error executing request", e);
    }
}
