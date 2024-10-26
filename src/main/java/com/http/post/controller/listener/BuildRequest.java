package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.*;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import java.util.List;

import static com.http.post.controller.listener.CreateRequestForCreation.HEADER_TABLE;
import static com.http.post.controller.listener.CreateRequestForCreation.PARAMETER_TABLE;

public interface BuildRequest {

    default Request buildRequest(ViewManager view, String url, String method, String body) throws InvalidMethodException {
        RequestBuilder rb = RequestBuilder.getInstance()
                .addComponent(new Body("application/json", body));
        List<EntityJPanel> entityJPanels = view.getMainPanel().getEntityJPanels();
        entityJPanels.get(HEADER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> rb.addComponent(new Header(kv.getKey(), kv.getValue())));
        entityJPanels.get(PARAMETER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> rb.addComponent(new QueryParam(kv.getKey(), kv.getValue())));
        return rb.build(url, method);
    }
}
