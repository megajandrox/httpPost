package com.http.post.view.model;

import com.http.post.view.popup.SearchableItem;

import java.util.HashMap;
import java.util.Map;

public class RequestData implements SearchableItem {

    private Long id;
    private String url;
    private String method;
    private boolean isFavorite;
    private String body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters = new HashMap<>();

    public RequestData() {}

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

    @Override
    public String toString() {
        return url;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getSearchField() {
        return this.url;
    }
}
