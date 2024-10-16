package com.http.post.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class QueryParam implements HttpRequestComponent {

    private String key;
    private String value;
}
