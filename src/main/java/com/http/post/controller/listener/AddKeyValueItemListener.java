package com.http.post.controller.listener;

import com.http.post.view.ViewManager;
import com.http.post.view.table.KeyValue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddKeyValueItemListener implements ActionListener {

    private ViewManager view;
    private int idEntity;

    public AddKeyValueItemListener(ViewManager view, int idEntity) {
        this.view = view;
        this.idEntity = idEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.getMainPanel().getEntityJPanels().get(this.idEntity).getTableModel().addRow(new KeyValue("", ""));
    }
}
