package com.http.post.controller.listener;

import com.http.post.controller.worker.SingleExecutor;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.panel.EntityJPanel;
import com.http.post.view.popup.SearchableItem;
import com.http.post.view.table.KeyValue;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static com.http.post.controller.utils.CreateRequestForCreation.HEADER_TABLE;
import static com.http.post.controller.utils.CreateRequestForCreation.PARAMETER_TABLE;

public class UrlSearchListener implements ActionListener, SingleExecutor {

    private final ViewManager view;

    public UrlSearchListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField searchField = view.getSearchPanel().getSearchPopupComponent().getSearchField();
        if (e.getSource() == searchField) {
            execute();
        }
    }

    @Override
    public void actionPerform() throws Exception {
        JTextField searchField = view.getSearchPanel().getSearchPopupComponent().getSearchField();
        SearchableItem selectedItem = view.getSearchPanel().getSearchPopupComponent().getSelectedItem();
        if (searchField != null && selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            view.getMainPanel().getUrlPanel().getUrlField().setText(requestData.getUrl());
            view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedItem(requestData.getMethod());
            view.getMainPanel().getBodyPanel().getTextArea().setText(requestData.getBody());
            EntityJPanel headerJPanel = view.getMainPanel().getEntityJPanels().get(HEADER_TABLE);
            headerJPanel.getTableModel().getContent().clear();
            requestData.getHeaders().forEach((k,v) -> headerJPanel.getTableModel().addRow(new KeyValue(k, v)));
            EntityJPanel parameterJPanel = view.getMainPanel().getEntityJPanels().get(PARAMETER_TABLE);
            parameterJPanel.getTableModel().getContent().clear();
            requestData.getParameters().forEach((k,v) -> parameterJPanel.getTableModel().addRow(new KeyValue(k, v)));
        }
    }
}
