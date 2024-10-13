package com.http.post.model;


@lombok.Data
public class Body implements HttpRequestComponent {

    public Body(String content, String contentType) {
        this.contentType = contentType;
        this.content = content;
    }

    private Long id;
    private String contentType;
    private String content;
}
