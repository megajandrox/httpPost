package com.http.post.controller.listener;

import com.http.post.controller.worker.JobExecutor;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.panel.EntityJPanel;
import com.http.post.view.table.KeyValue;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static com.http.post.controller.listener.CreateRequestForCreation.HEADER_TABLE;
import static com.http.post.controller.listener.CreateRequestForCreation.PARAMETER_TABLE;

public class UrlSearchListener implements ActionListener, JobExecutor {

    private final ViewManager view;

    public UrlSearchListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<RequestData> urlField = view.getSearchPanel().getUrlSearch();
        if (e.getSource() == urlField) {
            execute();
        }
    }

    @Override
    public void enableButton() { }

    @Override
    public void disableButton() { }

    @Override
    public void actionPerform() throws Exception {
        JComboBox<RequestData> urlField = view.getSearchPanel().getUrlSearch();
        if (urlField.getSelectedItem() != null && urlField.getSelectedItem() instanceof RequestData) {
            RequestData selectedItem = (RequestData) urlField.getSelectedItem();
            view.getMainPanel().getUrlPanel().getUrlField().setText(selectedItem.getUrl());
            view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedItem(selectedItem.getMethod());
            view.getMainPanel().getBodyPanel().getTextArea().setText(selectedItem.getBody());
            EntityJPanel headerJPanel = view.getMainPanel().getEntityJPanels().get(HEADER_TABLE);
            headerJPanel.getTableModel().getContent().clear();
            selectedItem.getHeaders().forEach((k,v) -> headerJPanel.getTableModel().addRow(new KeyValue(k, v)));
            EntityJPanel parameterJPanel = view.getMainPanel().getEntityJPanels().get(PARAMETER_TABLE);
            parameterJPanel.getTableModel().getContent().clear();
            selectedItem.getParameters().forEach((k,v) -> parameterJPanel.getTableModel().addRow(new KeyValue(k, v)));
        }
    }
}
