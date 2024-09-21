package com.http.post;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RequestBuilderTest {

    @Test
    public void testBuild() throws InvalidMethodException {
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

    @Test
    public void testBuildInvalidMethod(){
        try {
            RequestBuilder.getInstance()
                    .addComponent(new Body("body", "application/json"))
                    .addComponent(new Header("header", "value"))
                    .addComponent(new QueryParam("queryParam", "value"))
                    .build("url", "PATCH");
            fail("Expected InvalidMethodException");
        } catch (InvalidMethodException e) {
            assertEquals("Invalid method: PATCH", e.getMessage());
        }
    }
}