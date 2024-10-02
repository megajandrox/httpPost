package com.http.post.controller;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.*;
import com.http.post.service.SingleRunner;
import com.http.post.view.ViewManager;
import com.http.post.view.buttonPanel.ExecuteButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RequestController {

    private ViewManager view;

    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        view.getRequestPanel().getButtonPanel().getExecuteButton().addActionListener(new IncrementButtonListener());
        //view.getResetButton().addActionListener(new ResetButtonListener());
    }

    class IncrementButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ExecuteButton executeButton = view.getRequestPanel().getButtonPanel().getExecuteButton();
            executeButton.setEnabled(false);
            executeButton.setText("Executing...");
            String methodSelected = executeButton.getRequestPanel().getMethodSelected();
            Request request = new Request(executeButton.getRequestPanel().getURL(), Method.valueOf(methodSelected));
            executeButton.getRequestPanel().getParameters().forEach(kv -> request.addQueryParam(new QueryParam(kv.getKey(), kv.getValue())));
            executeButton.getRequestPanel().getHeaders().forEach(kv -> request.addHeader(new Header(kv.getKey(), kv.getValue())));
            request.setBody(new Body(executeButton.getRequestPanel().getRequestBodyArea().getText(), "application/json"));
            System.out.println(request);
            SingleRunner runner = new SingleRunner(request);
            try {
                Response response = runner.execute();
                System.out.println(response);
                view.getRequestPanel().setResponseBodyArea(response.getBody());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (RequestExecutionException ex) {
                throw new RuntimeException(ex);
            } catch (InvalidMethodException ex) {
                throw new RuntimeException(ex);
            } finally {
                executeButton.setEnabled(true);
                executeButton.setText("Execute");
            }
        }
    }

}
