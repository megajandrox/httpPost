package com.http.post.service;

import com.http.post.RequestBuilder;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Header;
import com.http.post.model.QueryParam;
import com.http.post.model.Request;
import com.http.post.model.Response;
import org.junit.Test;
import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class GetRequestTest extends WireMockHttpClientTest {

    @Test
    public void testExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s",ENDPOINT), "GET");
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getStatusMessage(), "OK");
        assertEquals(response.getContentType(), CONTENT_TYPE);
        assertEquals(response.getBody(), GET_MSG);
    }

    @Test
    public void testQueryParamExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new QueryParam("param1", "value1"));
        Request request = builder.build(format(BASE_URL + "%s",ENDPOINT), "GET");
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.getStatusMessage(), "OK");
        assertEquals(response.getContentType(), CONTENT_TYPE);
        assertEquals(response.getBody(), GET_MSG);
    }
}