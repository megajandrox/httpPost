package com.http.post.controller.utils;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.Request;
import com.http.post.view.ViewManager;

public abstract class CreateRequestForCreation implements BuildRequest {

    public static final int HEADER_TABLE = 0;
    public static final int PARAMETER_TABLE = 1;
    protected ViewManager view;

    public CreateRequestForCreation(ViewManager view) {
        this.view = view;
    }

    public Request createRequest() throws InvalidMethodException {
        String bodyText = view.getMainPanel().getBodyPanel().getTextArea().getText();
        String url = view.getMainPanel().getUrlPanel().getUrl();
        String method = view.getMainPanel().getUrlPanel().getMethod();
        return buildRequest(view, url, method, bodyText);
    }

}
