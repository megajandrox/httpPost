package com.http.post.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * This class is used to convert the input stream to string
 */
public class InputStreamUtils {

    /**
     * Convert the input stream to string
     * @param inputStream input stream
     * @return String response
     * @throws IOException if there is an error in the connection
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
}
