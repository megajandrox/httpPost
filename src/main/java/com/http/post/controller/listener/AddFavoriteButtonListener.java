package com.http.post.controller.listener;

import com.http.post.controller.utils.CreateRequestForUpdate;
import com.http.post.controller.worker.ButtonExecutor;
import com.http.post.controller.worker.Refreshable;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.Request;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.UpdateException;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class AddFavoriteButtonListener extends CreateRequestForUpdate implements ButtonExecutor , Refreshable {

    public AddFavoriteButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getFavoriteButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getFavoriteButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        try {
            Optional<Request> optRequest = Locator.getInstance().getRequestDAO().get(createRequest());
            if (optRequest.isPresent()) {
                Request request = optRequest.get();
                request.setFavorite(!request.getFavorite());
                Locator.getInstance().getRequestDAO().update(request);
                refresh();
                JOptionPane.showMessageDialog(view, "Set favorite to : " + request.getFavorite(),
                        "Favorite", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (InvalidMethodException | UpdateException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot save request",
                    "Save", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void addData(RequestData requestData) {
        this.view.getSearchPanel().getSearchPopupComponent().addData(requestData);
    }

    @Override
    public void clearData() {
        this.view.getSearchPanel().getSearchPopupComponent().clearData();
    }
}
