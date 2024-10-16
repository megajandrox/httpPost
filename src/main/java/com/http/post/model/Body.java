package com.http.post.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class Body implements HttpRequestComponent {

    private String contentType;
    private String content;
}
