package com.http.post.controller.listener;

import com.http.post.controller.utils.CleanUpRequest;
import com.http.post.controller.worker.ButtonExecutor;
import com.http.post.controller.worker.Refreshable;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.DeletionException;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener, ButtonExecutor, CleanUpRequest , Refreshable {

    public static final String EMPTY = "";
    public static final int FIRST_INDEX = 0;
    private ViewManager view;

    public DeleteButtonListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getDeleteButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getDeleteButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        Object selectedItem = this.view.getSelectedRequestData();
        if(selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            try {
                Locator.getInstance().getRequestDAO().delete(requestData.getId());
                cleanUpRequestComponents(this.view);
                refresh();
            } catch (DeletionException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(view, "Cannot delete request",
                        "Delete", JOptionPane.ERROR_MESSAGE);
            }
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
