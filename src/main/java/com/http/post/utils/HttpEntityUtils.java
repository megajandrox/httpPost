package com.http.post.utils;

import com.http.post.exceptions.RequestExecutionException;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to get the content type of the response
 */
public class HttpEntityUtils {

    /**
     * Get the content type of the response
     * @param entity response entity
     * @return String content type
     * @throws RequestExecutionException if there is an error in the request
     */
    public static String getContentType(org.apache.http.HttpEntity entity) throws RequestExecutionException {
        try (InputStream inputStream = entity.getContent()) {
            return InputStreamUtils.convertInputStreamToString(inputStream);
        } catch (IOException e) {
            throw new RequestExecutionException(e);
        }
    }
}
