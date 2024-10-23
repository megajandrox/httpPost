package com.http.post.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.http.post.utils.exceptions.JsonMappingException;

public class ToJson {

    public static <T> String toJson(T object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(e);
        }
    }

}
