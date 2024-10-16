package com.http.post.service;

import com.http.post.model.RequestBuilder;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Body;
import com.http.post.model.Header;
import com.http.post.model.Request;
import com.http.post.dto.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class PutRequestTest extends WireMockHttpClientTest {

    @Test
    public void testExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s",ENDPOINT), "PUT");
        request.setBody(new Body("{\"message\": \"success\"}", CONTENT_TYPE));
        SingleRunner runner = new SingleRunner(request);
        HttpResponse httpResponse = runner.execute();
        assertEquals(httpResponse.getStatusCode(), 201);
        assertEquals(httpResponse.getStatusMessage(), "Created");
        assertEquals(httpResponse.getContentType(), "application/json");
        assertEquals(httpResponse.getBody(), POST_MSG);
    }

}