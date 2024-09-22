package com.http.post.service.build;

import com.http.post.model.Request;
import com.http.post.utils.InputStreamUtils;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.BasicHttpEntity;

/**
 * This class is used to execute a put request. It is used to execute a request and get the response.
 */
public class PutStrategy implements RequestStrategy {
    @Override
    public HttpRequestBase createRequest(Request request) {
        HttpPut requestBase = new HttpPut(request.getFullUrl());
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(InputStreamUtils.convertStringToInputStream(request.getBody().getContent()));
        entity.setContentType(request.getBody().getContentType());
        requestBase.setEntity(entity);
        return addHeaders(requestBase, request);
    }
}
