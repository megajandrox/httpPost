package com.http.post.model;

public class Header extends Entity implements HttpRequestComponent {

    private String key;
    private String value;
    private Long requestId;
    public Header() {}

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Header(Long requestId, String key, String value) {
        this.requestId = requestId;
        this.key = value;
        this.value = value;
    }

    public String toString() {
        return String.format("Header{key='%s', value='%s'}", key, value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
}
