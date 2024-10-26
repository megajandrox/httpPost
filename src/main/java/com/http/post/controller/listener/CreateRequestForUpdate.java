package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.Request;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;

public class CreateRequestForUpdate extends CreateRequestForCreation {

    public CreateRequestForUpdate(ViewManager view) {
        super(view);
    }

    public Request create() throws InvalidMethodException {
        Request request = super.create();
        Object selectedItem = view.getSearchPanel().getUrlSearch().getSelectedItem();
        if (selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            request.setId(requestData.getId());
        }
        return request;
    }

}
