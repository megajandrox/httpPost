package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.Response;
import com.http.post.service.SingleRunner;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SendButtonListener extends RequestHandler implements ActionListener {

    public SendButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sendButton = view.getMainPanel().getButtonPanel().getSendButton();
        sendButton.setEnabled(false);
        try {
            SingleRunner runner = new SingleRunner(createRequest());
            Response response = runner.execute();
            view.getMainPanel().getResponsePanel().getTextArea().setText(response.getBody());
        } catch (IOException | RequestExecutionException | InvalidMethodException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot send request",
                    "Send Request", JOptionPane.ERROR_MESSAGE);
        } finally {
            sendButton.setEnabled(true);
        }
    }
}
