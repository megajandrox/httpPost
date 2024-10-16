package com.http.post.view.model;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
public class RequestData {

    private Long id;
    private String url;
    private String method;
    private boolean isFavorite;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();



    public RequestData(String fullUrl, String string, String content) {
        this.url = fullUrl;
        this.method = string;
        this.body = content;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }

    public String toString() {
        return this.url;
    }
}
