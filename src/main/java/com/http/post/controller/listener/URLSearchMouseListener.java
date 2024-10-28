package com.http.post.controller.listener;

import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.panel.EntityJPanel;
import com.http.post.view.popup.SearchPopupComponent;
import com.http.post.view.popup.SearchableItem;
import com.http.post.view.table.KeyValue;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.http.post.controller.utils.CreateRequestForCreation.HEADER_TABLE;
import static com.http.post.controller.utils.CreateRequestForCreation.PARAMETER_TABLE;

public class URLSearchMouseListener extends MouseAdapter {

    private ViewManager view;

    public URLSearchMouseListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            JTextField searchField = view.getSearchPanel().getSearchPopupComponent().getSearchField();
            SearchPopupComponent searchPopupComponent = view.getSearchPanel().getSearchPopupComponent();
            JList<SearchableItem> resultList = searchPopupComponent.getResultList();
            SearchableItem selectedValue = resultList.getSelectedValue();
            if (selectedValue != null) {
                searchField.setText(selectedValue.getSearchField());
                searchPopupComponent.getSearchPopup().setVisible(false);
                setSelectedItem(selectedValue);
            }
        }
    }

    private void setSelectedItem(SearchableItem selectedValue) {
        RequestData requestData = (RequestData) selectedValue;
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
