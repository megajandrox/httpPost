package com.http.post.utils;

import org.apache.http.client.config.RequestConfig;

public class RequestConfigUtils {

    /**
     * Configure the request
     * @return RequestConfig request configuration
     */
    public static RequestConfig basicConfiguration() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setContentCompressionEnabled(false)
                .build();
    }
}
