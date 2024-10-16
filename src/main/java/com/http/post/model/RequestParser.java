package com.http.post.model;


import com.http.post.utils.json.ToJson;

import static com.http.post.utils.json.FromJson.fromJson;

public class RequestParser {

    public static Request parse(String json) {
        return fromJson(json, Request.class).orElse(null);
    }

    public static String toJson(Request request) {
        return ToJson.toJson(request);
    }
}
