package com.http.post.utils.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonMappingException extends RuntimeException {

    public JsonMappingException(JsonProcessingException e) {
        super(e);
    }
}
