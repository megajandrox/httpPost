package com.http.post.service;

import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Request;
import com.http.post.model.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

//TODO: factory pattern to create different request types
public class SingleRunner {

    private Request request;

    public SingleRunner(Request request) {
        this.request = request;
    }

    //TODO: Create an strategy pattern to execute different requests methods
    public Response execute() throws IOException, RequestExecutionException {
        RequestConfig requestConfig = getRequestConfig();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {
            String queryParams = getQueryParams();
            HttpGet getRequest = new HttpGet(queryParams.isEmpty() ? this.request.getUrl() : this.request.getUrl() + "?" + queryParams);
            // TODO: Create an strategy pattern to execute different requests methods
            // HttpPost postRequest = new HttpPost(queryParams.isEmpty() ? this.request.getUrl() : this.request.getUrl() + "?" + queryParams);
            addHeaders(getRequest);
            try (CloseableHttpResponse response = httpClient.execute(getRequest)) {
                Response responseModel = new Response();
                HttpEntity entity = response.getEntity();
                try (InputStream inputStream = entity.getContent()) {
                    String responseString = convertInputStreamToString(inputStream);
                    responseModel.setBody(responseString);
                    responseModel.setContentType(entity.getContentType().getValue());
                } catch (IOException e) {
                    throw new RequestExecutionException(e);
                }
                responseModel.setStatusCode(response.getStatusLine().getStatusCode());
                responseModel.setStatusMessage(response.getStatusLine().getReasonPhrase());
                return responseModel;
            }
        }
    }

    private void addHeaders(HttpGet getRequest) {
        this.request.getHeaders().forEach(header -> getRequest.addHeader(header.getKey(), header.getValue()));
    }

    private String getQueryParams() {
        return this.request.getQueryParams().stream()
                .map(qp -> String.format("%s=%s", qp.getKey(), qp.getValue())).collect(Collectors.joining("&"));
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
               .setConnectTimeout(5000)
               .setSocketTimeout(5000)
               .setConnectionRequestTimeout(5000)
               .setContentCompressionEnabled(false)
               .build();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
