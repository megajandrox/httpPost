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
import java.awt.event.ActionListener;

public class UpdateButtonListener extends CreateRequestForUpdate implements ActionListener, ButtonExecutor, Refreshable {

    public UpdateButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getUpdateButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getUpdateButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        try {
            Request request = createRequest();
            System.out.println(request);
            Locator.getInstance().getRequestDAO().update(request);
            refresh();
            JOptionPane.showMessageDialog(view, "Updated successfully",
                    "Update", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidMethodException | UpdateException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot update the request",
                    "Update", JOptionPane.ERROR_MESSAGE);
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
