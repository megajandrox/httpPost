package com.http.post.utils.formatter;

public class DefaultFormatter implements Formatter {
    @Override
    public String format(String input) throws FormatterException {
        return input;
    }
}
