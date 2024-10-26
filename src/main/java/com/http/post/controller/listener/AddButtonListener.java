package com.http.post.controller.listener;

import com.http.post.controller.utils.CleanUpRequest;
import com.http.post.controller.utils.CreateRequestForCreation;
import com.http.post.controller.worker.ButtonExecutor;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.CreateException;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddButtonListener extends CreateRequestForCreation implements ActionListener, ButtonExecutor, CleanUpRequest {

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
        cleanUpRequestComponents(this.view);
        try {
            Locator.getInstance().getRequestDAO().create(createRequest());
            JOptionPane.showMessageDialog(view, "Created successfully",
                    "Create", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidMethodException | CreateException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot create the request",
                    "Create", JOptionPane.ERROR_MESSAGE);
        }
    }
}
