package com.http.post.controller.worker;

import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.SearchException;
import com.http.post.view.model.RequestData;
import com.http.post.model.Request;

public interface Refreshable {

    void addData(RequestData requestData);

    void clearData();

    default void refresh() {
        clearData();
        try {
            Locator.getInstance().getRequestDAO().getAll()
                    .stream().filter(Request::getFavorite)
                    .forEach(r -> {
                        String content = r.getBody() != null ? r.getBody().getContent() : "";
                        RequestData requestData = new RequestData(r.getUrl(), r.getMethod().toString(), content);
                        requestData.setId(r.getId());
                        r.getHeaders().forEach(h -> requestData.addHeader(h.getKey(), h.getValue()));
                        r.getQueryParams().forEach(p -> requestData.addParameter(p.getKey(), p.getValue()));
                        addData(requestData);
                    });
        } catch (SearchException e) {
            throw new RuntimeException(e);
        }
    }
}
