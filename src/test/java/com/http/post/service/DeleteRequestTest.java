package com.http.post.service;

import com.http.post.model.RequestBuilder;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Header;
import com.http.post.model.Request;
import com.http.post.model.Response;
import org.junit.Test;

import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DeleteRequestTest extends WireMockHttpClientTest {

    @Test
    public void testExecute() throws IOException, InvalidMethodException, RequestExecutionException {
        RequestBuilder builder = RequestBuilder.getInstance();
        builder.addComponent(new Header("Content-Type", CONTENT_TYPE));
        Request request = builder.build(format(BASE_URL + "%s/1",ENDPOINT), "DELETE");
        SingleRunner runner = new SingleRunner(request);
        Response response = runner.execute();
        assertEquals(response.getStatusCode(), 204);
        assertEquals(response.getStatusMessage(), "No Content");
        assertNull(response.getContentType());
        assertNull(response.getBody());
    }

}