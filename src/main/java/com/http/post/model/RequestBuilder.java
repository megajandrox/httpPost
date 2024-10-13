package com.http.post.model;

import com.http.post.exceptions.InvalidMethodException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to build a request
 * Is a builder pattern with singleton pattern
 */
public class RequestBuilder {

    private static final RequestBuilder INSTANCE = new RequestBuilder();
    private final List<HttpRequestComponent> httpRequestComponents = new ArrayList<>();

    public static RequestBuilder getInstance() {
        return INSTANCE;
    }

    public RequestBuilder addComponent(HttpRequestComponent httpRequestComponent) {
        httpRequestComponents.add(httpRequestComponent);
        return this;
    }

    private RequestBuilder() {}


    public Request build(String url, String method) throws InvalidMethodException {
        try {
            final Request request = new Request(url, Method.valueOf(method));
            httpRequestComponents.forEach(c -> {
                switch (c.getClass().getSimpleName()) {
                    case "Body":
                        request.setBody((Body) c);
                        break;
                    case "Header":
                        request.getHeaders().add((Header) c);
                        break;
                    case "QueryParam":
                        request.getQueryParams().add((QueryParam) c);
                        break;
                }
            });
            return request;
        } catch (IllegalArgumentException e) {
            throw new InvalidMethodException(method);
        } finally {
            httpRequestComponents.clear();
        }
    }

}
