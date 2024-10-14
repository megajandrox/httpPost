package com.http.post.service.build;

import com.http.post.model.Request;
import com.http.post.utils.InputStreamUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;

/**
 * This class is used to execute a post request. It is used to execute a request and get the response.
 */
public class PostStrategy implements RequestStrategy {
    @Override
    public HttpRequestBase createRequest(Request request) {
        HttpPost requestBase = new HttpPost(request.getFullUrl());
        BasicHttpEntity entity = new BasicHttpEntity();
        StringBuilder headerSB = request.getHeaders().stream().map(kv -> kv.getKey() + ":" + kv.getValue()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        StringBuilder qpSB = request.getQueryParams().stream().map(kv -> kv.getKey() + ":" + kv.getValue()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
        System.out.println(request.getBody().getContent());
        System.out.println(headerSB);
        System.out.println(qpSB);
        entity.setContent(InputStreamUtils.convertStringToInputStream(request.getBody().getContent()));
        entity.setContentType(request.getBody().getContentType());
        requestBase.setEntity(entity);
        return addHeaders(requestBase, request);
    }
}
