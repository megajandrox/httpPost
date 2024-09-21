package com.http.post.model;


import com.http.post.Component;

@lombok.Data
public class Body implements Component {

    public Body(String content, String contentType) {
        this.contentType = contentType;
        this.content = content;
    }

    private Long id;
    private String contentType;
    private String content;
}
