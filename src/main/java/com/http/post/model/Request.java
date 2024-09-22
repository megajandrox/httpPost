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
}
