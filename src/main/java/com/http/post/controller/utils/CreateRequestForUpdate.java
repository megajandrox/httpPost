package com.http.post.controller.utils;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.Request;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;

public abstract class CreateRequestForUpdate extends CreateRequestForCreation {

    public CreateRequestForUpdate(ViewManager view) {
        super(view);
    }

    public Request createRequest() throws InvalidMethodException {
        Request request = super.createRequest();
        Object selectedItem = view.getSearchPanel().getUrlSearch().getSelectedItem();
        if (selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            request.setId(requestData.getId());
        }
        return request;
    }

}
