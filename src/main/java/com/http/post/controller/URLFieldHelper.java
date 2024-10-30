package com.http.post.controller;

import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.SearchException;
import com.http.post.view.model.RequestData;
import com.http.post.view.popup.SearchPopupComponent;

public class URLFieldHelper {

    public static void populateHttpRequest(SearchPopupComponent searchPopupComponent) throws SearchException {
        searchPopupComponent.clearData();
        Locator.getInstance().getRequestDAO().getAll().forEach(r -> {
            String content = r.getBody() != null ? r.getBody().getContent() : "";
            RequestData requestData = new RequestData(r.getUrl(), r.getMethod().toString(), content);
            requestData.setId(r.getId());
            r.getHeaders().forEach(h -> requestData.addHeader(h.getKey(), h.getValue()));
            r.getQueryParams().forEach(p -> requestData.addParameter(p.getKey(), p.getValue()));
            searchPopupComponent.addData(requestData);
        });
    }

}
