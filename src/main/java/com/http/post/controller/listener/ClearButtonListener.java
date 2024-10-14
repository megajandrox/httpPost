package com.http.post.controller.listener;

import com.http.post.view.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearButtonListener implements ActionListener {

    public static final String EMPTY = "";
    public static final int FIRST_INDEX = 0;
    private ViewManager view;

    public ClearButtonListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getMainPanel().getEntityJPanels().forEach(EntityJPanel -> EntityJPanel.getTableModel().removeAllRows());
        view.getMainPanel().getBodyPanel().getTextArea().setText(EMPTY);
        view.getMainPanel().getResponsePanel().getTextArea().setText(EMPTY);
        view.getMainPanel().getUrlPanel().getUrlField().setText(EMPTY);
        view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedIndex(FIRST_INDEX);
    }
}
