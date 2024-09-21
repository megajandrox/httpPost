package com.http.post;

import com.http.post.model.*;
import junit.framework.TestCase;

public class RequestBuilderTest extends TestCase {

    public void testBuild() {
        Request request = RequestBuilder.getInstance()
                .addComponent(new Body("body", "application/json"))
                .addComponent(new Header("header", "value"))
                .addComponent(new QueryParam("queryParam", "value"))
                .build("url", "POST");
        assertEquals("url", request.getUrl());
        assertEquals(Method.POST, request.getMethod());
        assertEquals("body", request.getBody().getContent());
        assertEquals("application/json", request.getBody().getContentType());
        assertEquals("value", request.getHeaders().get(0).getValue());
        assertEquals("value", request.getQueryParams().get(0).getValue());
    }
}