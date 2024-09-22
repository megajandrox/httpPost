package com.http.post.service.build;

import com.http.post.model.Request;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * This class is used to execute a get request. It is used to execute a request and get the response.
 */
public class GetStrategy implements RequestStrategy {
    @Override
    public HttpRequestBase createRequest(Request request) {
        HttpRequestBase requestBase = new HttpGet(request.getFullUrl());
        return addHeaders(requestBase, request);
    }
}
