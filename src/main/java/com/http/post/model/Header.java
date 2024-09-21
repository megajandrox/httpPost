package com.http.post.model;

import com.http.post.Component;

@lombok.Data
public class Header implements Component {

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }
    private Long id;
    private String key;
    private String value;
}
