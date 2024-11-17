package com.http.post.view.panel;

public interface HandleProtocolResponse {

    /**
     * Example
     * HTTP/1.1 200 OK
     * Date: Mon, 27 Jul 2009 12:28:53 GMT
     * Server: Apache/2.2.14 (Win32)
     * Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
     * Content-Length: 88
     * Content-Type: text/html
     * Connection: Closed
     */
    void setResponse(String protocolResponse);
}
