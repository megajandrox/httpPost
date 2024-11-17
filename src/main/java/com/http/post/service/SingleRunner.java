package com.http.post.service;

import com.http.post.dto.HttpResponse;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Request;
import com.http.post.service.build.DeleteStrategy;
import com.http.post.service.build.GetStrategy;
import com.http.post.service.build.PostStrategy;
import com.http.post.service.build.PutStrategy;
import com.http.post.utils.HttpEntityUtils;
import com.http.post.utils.RequestConfigUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * This class is used to execute a single request. It is the most basic class to use.
 * It is used to execute a request and get the response.
 * <p>
 */
public class SingleRunner {

    public static final String SPACE = " ";
    public static final String COLON = " : ";
    public static final String LINE_BREAK = "\n";
    private final Request request;
    public SingleRunner(Request request) {
        this.request = request;
    }

    /**
     * Factory method to create the request based on the method
     * @return HttpRequestBase request to execute  {@link HttpRequestBase}
     * @throws InvalidMethodException if the method is not supported
     */
    private HttpRequestBase factoryStrategy() throws InvalidMethodException {
        switch (this.request.getMethod()) {
            case GET:
                return new GetStrategy().createRequest(this.request);
            case POST:
                return new PostStrategy().createRequest(this.request);
            case PUT:
                return new PutStrategy().createRequest(this.request);
            case DELETE:
                return new DeleteStrategy().createRequest(this.request);
        }
        throw new InvalidMethodException(this.request.getMethod().toString());
    }

    /**
     * Execute the request and get the response
     * @return Response response of the request
     * @throws IOException if there is an error in the connection
     * @throws RequestExecutionException if there is an error in the request
     * @throws InvalidMethodException if the method is not supported
     */
    public HttpResponse execute() throws IOException, RequestExecutionException, InvalidMethodException {
        RequestConfig requestConfig = RequestConfigUtils.basicConfiguration();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {
            HttpRequestBase httpRequest = factoryStrategy();
            System.out.println(httpRequest.toString());
            try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
                HttpResponse httpResponseModel = new HttpResponse();
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    httpResponseModel.setBody(HttpEntityUtils.getContentType(entity));
                    //System.out.println("Response ContentType: " + entity.getContentType());
                    httpResponseModel.setContentType(entity.getContentType() != null ? entity.getContentType().getValue() : null);
                }
                httpResponseModel.setStatusCode(response.getStatusLine().getStatusCode());
                httpResponseModel.setStatusMessage(response.getStatusLine().getReasonPhrase());
                StringBuilder sb = new StringBuilder();
                sb.append(response.getStatusLine().getProtocolVersion())
                        .append(SPACE)
                        .append(response.getStatusLine().getStatusCode())
                        .append(SPACE)
                        .append(response.getStatusLine().getReasonPhrase())
                        .append(LINE_BREAK);
                HeaderIterator it = response.headerIterator();
                while (it.hasNext()) {
                    Header header = it.nextHeader();
                    sb.append(header.getName()).append(COLON).append(header.getValue()).append(LINE_BREAK);;
                }
                httpResponseModel.setHttpProtocolResultMessage(sb.toString());
                httpClient.close();
                return httpResponseModel;
            }
        }
    }
}
