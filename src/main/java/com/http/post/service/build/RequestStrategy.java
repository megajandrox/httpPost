package com.http.post.service.build;

import com.http.post.model.Request;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Interface to create the request based on the method
 */
public interface RequestStrategy {

    HttpRequestBase createRequest(Request request);

    default HttpRequestBase addHeaders(HttpRequestBase requestBase, Request request) {
        request.getHeaders().forEach(header -> requestBase.addHeader(header.getKey(), header.getValue()));
        return requestBase;
    }
}
