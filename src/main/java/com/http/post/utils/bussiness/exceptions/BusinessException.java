package com.http.post.utils.bussiness.exceptions;

public class BusinessException extends Exception {

    public BusinessException(String msg) {    super(msg); }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
