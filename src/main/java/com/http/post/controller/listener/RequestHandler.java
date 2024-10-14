package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.*;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import java.util.List;

public class RequestHandler {

    public static final int HEADER_TABLE = 0;
    public static final int PARAMETER_TABLE = 1;
    protected ViewManager view;

    public RequestHandler(ViewManager view) {
        this.view = view;
    }

    public Request createRequest() throws InvalidMethodException {
        RequestBuilder requestBuilder = RequestBuilder.getInstance()
                .addComponent(new Body(view.getMainPanel().getBodyPanel().getTextArea().getText(), "application/json"));
        String url = view.getMainPanel().getUrlPanel().getUrl();
        String method = view.getMainPanel().getUrlPanel().getMethod();
        List<EntityJPanel> entityJPanels = view.getMainPanel().getEntityJPanels();
        entityJPanels.get(HEADER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> requestBuilder.addComponent(new Header(kv.getKey(), kv.getValue())));
        entityJPanels.get(PARAMETER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> requestBuilder.addComponent(new QueryParam(kv.getKey(), kv.getValue())));
        return requestBuilder.build(url, method);
    }
}
