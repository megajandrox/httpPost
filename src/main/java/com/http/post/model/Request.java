package com.http.post.model;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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
}
