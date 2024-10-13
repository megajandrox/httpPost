package com.http.post.controller;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.*;
import com.http.post.service.SingleRunner;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RequestController {

    private ViewManager view;

    //TODO: Implement clear button
    //TODO: Implement add to favorites button
    //TODO: Implement message errors dialog and custom exceptions
    //TODO: Implement show response status code, status message and headers
    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        this.view.getMainPanel().getButtonPanel().getSendButton().addActionListener(new SendButtonListener());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sendButton = view.getMainPanel().getButtonPanel().getSendButton();
            sendButton.setEnabled(false);
            sendButton.setText("Executing...");
            String method = view.getMainPanel().getUrlPanel().getMethod();
            String url = view.getMainPanel().getUrlPanel().getUrl();

            Request request = new Request(url, Method.valueOf(method));
            //view.getMainPanel().getParameters().forEach(kv -> request.addQueryParam(new QueryParam(kv.getKey(), kv.getValue())));
            //executeButton.getRequestPanel().getHeaders().forEach(kv -> request.addHeader(new Header(kv.getKey(), kv.getValue())));
            //request.setBody(new Body(executeButton.getRequestPanel().getRequestBodyArea().getText(), "application/json"));
            SingleRunner runner = new SingleRunner(request);
            try {
                Response response = runner.execute();
                view.getMainPanel().getResponsePanel().getTextArea().setText(response.getBody());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (RequestExecutionException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidMethodException ex) {
                throw new RuntimeException(ex);
            } finally {
                sendButton.setEnabled(true);
                sendButton.setText("Send");
            }
        }
    }

}
