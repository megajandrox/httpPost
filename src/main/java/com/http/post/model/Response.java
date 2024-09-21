package com.http.post.model;

import lombok.Data;

@Data
public class Response {

    private String body;
    private int statusCode;
    private String statusMessage;
    private String contentType;

}
