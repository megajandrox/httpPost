package com.http.post.utils.image;

import com.http.post.utils.formatter.Formatter;
import com.http.post.utils.formatter.FormatterException;

import java.util.Base64;

public class ImageFormatter implements Formatter {

    public String format(String content) throws FormatterException {
        return Base64.getEncoder().encodeToString(content.getBytes());
    }
}
