package com.http.post.view.panel;

import javax.swing.*;
import java.awt.*;
import com.http.post.view.table.EntityTableModel;

public abstract class EntityJPanel<T> extends JPanel {

    private String tableName;

    public EntityJPanel(String tableName) {
        super(new BorderLayout());
        this.tableName = tableName;
    }

    public abstract JTable getTable();
    public abstract EntityTableModel getTableModel();
    public abstract JButton getAddButton();
    public abstract JButton getRemoveButton();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
