package com.http.post.controller;

import com.http.post.repository.Locator;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.table.KeyValue;
import commons.db.utils.bussiness.exceptions.SearchException;

import javax.swing.*;

import static com.http.post.controller.listener.RequestHandler.HEADER_TABLE;
import static com.http.post.controller.listener.RequestHandler.PARAMETER_TABLE;

public class URLFieldHelper {

    public static void populateHttpRequest(JComboBox<RequestData> urlField) throws SearchException {
        urlField.removeAllItems();
        Locator.getInstance().getRequestDAO().getAll().forEach(r -> {
            String content = r.getBody() != null ? r.getBody().getContent() : "";
            RequestData requestData = new RequestData(r.getFullUrl(), r.getMethod().toString(), content);
            requestData.setId(r.getId());
            r.getHeaders().forEach(h -> requestData.addHeader(h.getKey(), h.getValue()));
            r.getQueryParams().forEach(p -> requestData.addParameter(p.getKey(), p.getValue()));
            urlField.addItem(requestData);
        });
    }

    public static void setRequestDataOnView(RequestData requestData, ViewManager view) {
        view.getMainPanel().getUrlPanel().getUrlField().setSelectedItem(requestData);
        view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedItem(requestData.getMethod());
        view.getMainPanel().getBodyPanel().getTextArea().setText(requestData.getBody());
        view.getMainPanel().getResponsePanel().getTextArea().setText(requestData.getBody());
        view.getMainPanel().getEntityJPanels().forEach(EntityJPanel -> EntityJPanel.getTableModel().removeAllRows());
        requestData.getHeaders().forEach((k,v) -> view.getMainPanel().getEntityJPanels().get(HEADER_TABLE).getTableModel().addRow(new KeyValue(k, v)));
        requestData.getParameters().forEach((k,v) -> view.getMainPanel().getEntityJPanels().get(PARAMETER_TABLE).getTableModel().addRow(new KeyValue(k, v)));
    }
}
