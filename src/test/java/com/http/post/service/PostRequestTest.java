package com.http.post.service;

import com.http.post.dto.HttpResponse;
import com.http.post.model.RequestBuilder;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Body;
import com.http.post.model.Header;
import com.http.post.model.Request;
import org.junit.Test;

import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PostRequestTest extends WireMockHttpClientTest {

    @Test
    public void testExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 201);
        assertEquals(httpResponse.getStatusMessage(), "Created");
        assertEquals(httpResponse.getContentType(), "application/json");
        assertEquals(httpResponse.getBody(), POST_MSG);
    }

    @Test
    public void testNotFoundExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/pepe",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 404);
        assertEquals(httpResponse.getStatusMessage(), "Not Found");
        assertEquals(httpResponse.getContentType(), "text/plain");
    }

    @Test
    public void testBadRequestExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/bad",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 400);
        assertEquals(httpResponse.getStatusMessage(), "Bad Request");
        assertNull(httpResponse.getContentType());
    }

    @Test
    public void testUnauthorizedExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Unauthorized",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 401);
        assertEquals(httpResponse.getStatusMessage(), "Unauthorized");
        assertNull(httpResponse.getContentType());
    }

    @Test
    public void testForbiddenExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Forbidden",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 403);
        assertEquals(httpResponse.getStatusMessage(), "Forbidden");
        assertNull(httpResponse.getContentType());
    }

    @Test
    public void testConflictExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Conflict",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 409);
        assertEquals(httpResponse.getStatusMessage(), "Conflict");
        assertNull(httpResponse.getContentType());
    }
}