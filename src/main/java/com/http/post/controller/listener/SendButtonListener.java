package com.http.post.controller.listener;

import com.http.post.exceptions.InvalidMethodException;
import com.http.post.exceptions.RequestExecutionException;
import com.http.post.model.*;
import com.http.post.service.SingleRunner;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SendButtonListener implements ActionListener {

    public static final int HEADER_TABLE = 0;
    public static final int PARAMETER_TABLE = 1;
    private ViewManager view;

    public SendButtonListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton sendButton = view.getMainPanel().getButtonPanel().getSendButton();
        sendButton.setEnabled(false);
        String method = view.getMainPanel().getUrlPanel().getMethod();
        String url = view.getMainPanel().getUrlPanel().getUrl();
        RequestBuilder requestBuilder = RequestBuilder.getInstance()
                .addComponent(new Body(view.getMainPanel().getBodyPanel().getTextArea().getText(), "application/json"));
        List<EntityJPanel> entityJPanels = view.getMainPanel().getEntityJPanels();
        entityJPanels.get(HEADER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> requestBuilder.addComponent(new Header(kv.getKey(), kv.getValue())));
        entityJPanels.get(PARAMETER_TABLE).getTableModel().getContent()
                .stream().filter(kv -> !kv.getKey().isEmpty() && !kv.getValue().isEmpty())
                .forEach(kv -> requestBuilder.addComponent(new QueryParam(kv.getKey(), kv.getValue())));
        try {
            SingleRunner runner = new SingleRunner(requestBuilder.build(url, method));
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
        }
    }
}
