package com.http.post.model;

import com.http.post.Component;

@lombok.Data
public class QueryParam implements Component {

    public QueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private Long id;
    private String key;
    private String value;
}
