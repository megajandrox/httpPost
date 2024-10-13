package com.http.post.model;

@lombok.Data
public class QueryParam implements HttpRequestComponent {

    public QueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private Long id;
    private String key;
    private String value;
}
