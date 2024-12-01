package com.http.post.model;

public class Body extends Entity implements HttpRequestComponent {

    private String contentType;
    private String content;
    private Long requestId;

    public Body() {}

    public Body(String contentType, String content) {
        this.contentType = contentType;
        this.content = content;
    }

    public String toString() {
        return String.format("Body{contentType='%s', content='%s'}", contentType, content);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}
