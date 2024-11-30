package com.http.post.model;

public class QueryParam extends Entity implements HttpRequestComponent {

    private String key;
    private String value;
    private Long requestId;

    public QueryParam() {}

    public QueryParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return String.format("QueryParam{key='%s', value='%s'}", key, value);
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
