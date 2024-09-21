package com.http.post.exceptions;

import java.io.IOException;

public class RequestExecutionException extends Throwable {

    public RequestExecutionException(IOException e) {
        super("Error executing request", e);
    }
}
