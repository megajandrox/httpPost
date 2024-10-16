package com.http.post.dto;

import lombok.Data;

@Data
public class HttpResponse {

    private String body;
    private int statusCode;
    private String statusMessage;
    private String contentType;

}
