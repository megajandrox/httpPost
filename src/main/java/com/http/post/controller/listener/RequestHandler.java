package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.*;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
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
        String bodyText = view.getMainPanel().getBodyPanel().getTextArea().getText();
        String url = view.getMainPanel().getUrlPanel().getUrl();
        String method = view.getMainPanel().getUrlPanel().getMethod();

        RequestBuilder rb = RequestBuilder.getInstance()
                .addComponent(new Body("application/json", bodyText));
        List<EntityJPanel> entityJPanels = view.getMainPanel().getEntityJPanels();
        entityJPanels.get(HEADER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> rb.addComponent(new Header(kv.getKey(), kv.getValue())));
        entityJPanels.get(PARAMETER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> rb.addComponent(new QueryParam(kv.getKey(), kv.getValue())));
        Object selectedItem = view.getMainPanel().getUrlPanel().getUrlField().getSelectedItem();
        Request request = rb.build(url, method);
        if (selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            request.setId(requestData.getId());
        }
        return request;
    }
}
