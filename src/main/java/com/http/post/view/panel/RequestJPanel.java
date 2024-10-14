package com.http.post.view.panel;

import com.http.post.view.table.EntityTableModel;
import com.http.post.view.table.KeyValue;

import javax.swing.*;
import java.awt.*;

public class RequestJPanel extends EntityJPanel<KeyValue> {

    private EntityTableModel tableModel;
    private JTable table;
    private JButton addButton;
    private JButton removeButton;


    public RequestJPanel(EntityTableModel tableModel) {
        super(tableModel.getTableName());
        setLayout(new BorderLayout());
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.addButton = new JButton("+");
        this.removeButton = new JButton("-");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.addButton);
        buttonPanel.add(this.removeButton);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        setPreferredSize(new Dimension(800, 600));
        this.tableModel = tableModel;
    }

    public JButton getAddButton() {
        return addButton;
    }

    @Override
    public JButton getRemoveButton() {
        return removeButton;
    }

    @Override
    public EntityTableModel getTableModel() {
        return this.tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
