package com.http.post.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.http.post.utils.exception.JsonMappingException;

import java.util.Optional;

public class FromJson {

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        T result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonMappingException(e);
        }
        return Optional.ofNullable(result);
    }

}
