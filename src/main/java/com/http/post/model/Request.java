package com.http.post.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

    public Request() {}

    public Request(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    private Long id;
    private String url;
    private Method method;
    private Body body;
    private List<Header> headers = new ArrayList<>();
    private List<QueryParam> queryParams = new ArrayList<>();

    private String getQueryParamsAsString() {
        return this.queryParams.stream()
                .map(qp -> String.format("%s=%s", qp.getKey(), qp.getValue())).collect(Collectors.joining("&"));
    }

    public String getFullUrl() {
        return queryParams.isEmpty() ? this.getUrl() : this.getUrl() + "?" + getQueryParamsAsString();
    }

    public String toString() {
        return String.format("Request{url='%s', method='%s', body='%s', headers=%s, queryParams=%s}", url, method, body, headers, queryParams);
    }

    public String toJson() {
        return RequestParser.toJson(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<QueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<QueryParam> queryParams) {
        this.queryParams = queryParams;
    }
}
