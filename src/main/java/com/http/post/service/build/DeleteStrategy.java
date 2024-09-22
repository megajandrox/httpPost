package com.http.post.service.build;

import com.http.post.model.Request;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * This class is used to execute a delete request. It is used to execute a request and get the response.
 */
public class DeleteStrategy implements RequestStrategy {
    @Override
    public HttpRequestBase createRequest(Request request) {
        return null;
    }
}
