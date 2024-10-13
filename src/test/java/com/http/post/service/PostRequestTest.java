package com.http.post.service;

import com.http.post.model.RequestBuilder;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Body;
import com.http.post.model.Header;
import com.http.post.model.Request;
import com.http.post.model.Response;
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
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 201);
        assertEquals(response.getStatusMessage(), "Created");
        assertEquals(response.getContentType(), "application/json");
        assertEquals(response.getBody(), POST_MSG);
    }

    @Test
    public void testNotFoundExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/pepe",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 404);
        assertEquals(response.getStatusMessage(), "Not Found");
        assertEquals(response.getContentType(), "text/plain");
    }

    @Test
    public void testBadRequestExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/bad",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 400);
        assertEquals(response.getStatusMessage(), "Bad Request");
        assertNull(response.getContentType());
    }

    @Test
    public void testUnauthorizedExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Unauthorized",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 401);
        assertEquals(response.getStatusMessage(), "Unauthorized");
        assertNull(response.getContentType());
    }

    @Test
    public void testForbiddenExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Forbidden",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 403);
        assertEquals(response.getStatusMessage(), "Forbidden");
        assertNull(response.getContentType());
    }

    @Test
    public void testConflictExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/Conflict",ENDPOINT), "POST");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 409);
        assertEquals(response.getStatusMessage(), "Conflict");
        assertNull(response.getContentType());
    }
}