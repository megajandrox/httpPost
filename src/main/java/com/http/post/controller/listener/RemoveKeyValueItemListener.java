package com.http.post.controller.listener;

import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveKeyValueItemListener implements ActionListener {

    private ViewManager view;
    private int idEntity;

    public RemoveKeyValueItemListener(ViewManager view, int idEntity) {
        this.view = view;
        this.idEntity = idEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EntityJPanel entityJPanel = view.getMainPanel().getEntityJPanels().get(this.idEntity);
        int selectedRow = entityJPanel.getTable().getSelectedRow();
        if (selectedRow != -1) {
            entityJPanel.getTableModel().removeRow(selectedRow);
        }
    }
}
