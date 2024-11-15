package com.http.post.utils.formatter;

import com.http.post.utils.image.ImageFormatter;

public class ContextFormatter {

    private final Formatter formatter;

    public ContextFormatter(String contentType) {
        if(contentType == null || contentType.contains("json")) {
            this.formatter = new JsonFormatter();
        } else if(contentType.contains("xml")) {
            this.formatter = new XmlFormatter();
        } else if(contentType.contains("image")) {
            this.formatter = new ImageFormatter();
        } else {
            this.formatter = new DefaultFormatter();
        }
    }
    public String format(String content) throws FormatterException {
        return formatter.format(content);
    }
}
