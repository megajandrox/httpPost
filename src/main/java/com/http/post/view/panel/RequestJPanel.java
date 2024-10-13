package com.http.post.view.panel;

import db.model.Customer;
import db.view.panel.EntityJPanel;
import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;

public class RequestJPanel extends EntityJPanel<Customer> {

    private EntityTableModel<Customer> tableModel;
    private JTable table;
    private JButton addButton;
    private JButton removeButton;

    public RequestJPanel(String tableName) {
        super(tableName);
    }

    public RequestJPanel(EntityTableModel<Customer> tableModel) {
        this(tableModel.getTableName());
        setLayout(new BorderLayout());
        add(new JLabel("Params content goes here."), BorderLayout.CENTER);
        this.tableModel = tableModel;
        /*
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.addButton = new JButton("Add");
        add(addButton, BorderLayout.EAST);
        this.removeButton = new JButton("Remove");
        add(removeButton, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(800, 600));
        */
    }

    public JButton getAddButton() {
        return addButton;
    }

    @Override
    public JButton getRemoveButton() {
        return removeButton;
    }

    @Override
    public EntityTableModel<Customer> getTableModel() {
        return this.tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
