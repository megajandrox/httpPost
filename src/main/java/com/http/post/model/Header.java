package com.http.post.model;

@lombok.Data
public class Header implements HttpRequestComponent {

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }
    private Long id;
    private String key;
    private String value;
}
