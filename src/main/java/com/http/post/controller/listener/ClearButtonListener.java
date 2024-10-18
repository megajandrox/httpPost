package com.http.post.controller.listener;

import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.DeletionException;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.http.post.controller.URLFieldHelper.setRequestDataOnView;

public class ClearButtonListener implements ActionListener {

    public static final String EMPTY = "";
    public static final int FIRST_INDEX = 0;
    private ViewManager view;

    public ClearButtonListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object selectedItem = this.view.getMainPanel().getUrlPanel().getUrlField().getSelectedItem();
        if(selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            try {
                Locator.getInstance().getRequestDAO().delete(requestData.getId());
                JComboBox<RequestData> urlField = this.view.getMainPanel().getUrlPanel().getUrlField();
                urlField.removeItem(requestData);
                if(urlField.getItemCount() > 0) {
                    urlField.setSelectedIndex(FIRST_INDEX);
                    setRequestDataOnView(urlField.getItemAt(FIRST_INDEX), this.view);
                } else {
                    this.view.getMainPanel().getEntityJPanels().forEach(EntityJPanel -> EntityJPanel.getTableModel().removeAllRows());
                    this.view.getMainPanel().getBodyPanel().getTextArea().setText(EMPTY);
                    this.view.getMainPanel().getResponsePanel().getTextArea().setText(EMPTY);
                    this.view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedIndex(FIRST_INDEX);
                }
            } catch (DeletionException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(view, "Cannot delete request",
                        "Delete", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
