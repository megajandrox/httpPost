package com.http.post.model;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@lombok.Data
public class Request {

    public Request(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    private Long id;
    private String url;
    private Method method;
    @EqualsAndHashCode.Exclude
    private Body body;
    @EqualsAndHashCode.Exclude
    private List<Header> headers = new ArrayList<>();
    @EqualsAndHashCode.Exclude
    private List<QueryParam> queryParams = new ArrayList<>();

    private String getQueryParamsAsString() {
        return this.queryParams.stream()
                .map(qp -> String.format("%s=%s", qp.getKey(), qp.getValue())).collect(Collectors.joining("&"));
    }

    public String getFullUrl() {
        return queryParams.isEmpty() ? this.getUrl() : this.getUrl() + "?" + getQueryParamsAsString();
    }

    public void addQueryParam(QueryParam queryParam) {
        this.queryParams.add(queryParam);
    }

    public void addHeader(Header header) {
        this.headers.add(header);
    }

    public String toString() {
        return String.format("Request{url='%s', method='%s', body='%s', headers=%s, queryParams=%s}", url, method, body, headers, queryParams);
    }
}
