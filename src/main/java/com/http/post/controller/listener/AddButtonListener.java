package com.http.post.controller.listener;

import com.http.post.controller.utils.CreateRequestForCreation;
import com.http.post.controller.worker.ButtonExecutor;
import com.http.post.controller.worker.Refreshable;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.model.Request;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.CreateException;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener extends CreateRequestForCreation implements ActionListener, ButtonExecutor, Refreshable {

    public AddButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getAddButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getAddButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        try {
            Request request = createRequest();
            System.out.println(request);
            Locator.getInstance().getRequestDAO().create(request);
            RequestData requestData = new RequestData(request.getFullUrl(), request.getMethod().toString(), request.getBody().getContent());
            this.view.setSelectedRequestData(requestData);
            refresh();
            JOptionPane.showMessageDialog(view, "Created successfully",
                    "Create", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidMethodException | CreateException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot create the request",
                    "Create", JOptionPane.ERROR_MESSAGE);
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
