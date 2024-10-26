package com.http.post.controller.listener;

import com.http.post.controller.utils.CreateRequestForUpdate;
import com.http.post.controller.worker.ButtonExecutor;
import com.http.post.dto.HttpResponse;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.service.SingleRunner;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SendButtonListener extends CreateRequestForUpdate implements ActionListener, ButtonExecutor {

    public SendButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        view.getMainPanel().getUrlPanel().getSendButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        view.getMainPanel().getUrlPanel().getSendButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        try {
            SingleRunner runner = new SingleRunner(createRequest());
            HttpResponse httpResponse = runner.execute();
            view.getMainPanel().getResponsePanel().getTextArea().setText(httpResponse.getBody());
        } catch (IOException | RequestExecutionException | InvalidMethodException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot send request",
                    "Send Request", JOptionPane.ERROR_MESSAGE);
        }
    }
}
