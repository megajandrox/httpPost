package com.http.post.utils.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonFormatter implements Formatter  {

    public String format(String content) throws FormatterException {
        String prettyJson="";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(content, Object.class);

            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            prettyJson = writer.writeValueAsString(json);
            //System.out.println(prettyJson);
        } catch (Exception e) {
            throw new FormatterException("Error formatting JSON: " , e);
        }
        return prettyJson;
    }
}
