package com.http.post.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonFormatter {

    public static String format(String jsonString) throws JsonFormatterException {
        String prettyJson="";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(jsonString, Object.class);

            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            prettyJson = writer.writeValueAsString(json);
            System.out.println(prettyJson);
        } catch (Exception e) {
            throw new JsonFormatterException("Error formatting JSON: " , e);
        }
        return prettyJson;
    }
}
