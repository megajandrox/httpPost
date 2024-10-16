package com.http.post.controller;

import com.http.post.repository.Locator;
import com.http.post.view.model.RequestData;
import commons.db.utils.bussiness.exceptions.SearchException;

import javax.swing.*;

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
}
