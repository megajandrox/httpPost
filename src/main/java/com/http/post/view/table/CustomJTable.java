package com.http.post.view.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

public class CustomJTable extends JTable {

    private DefaultTableModel model;
    private JTable table;

    public CustomJTable(JButton addButton) {
        setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("Key");
        model.addColumn("Value");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 300, 150); // Set bounds for the scroll pane
        JTextField keyField = new JTextField(10);
        JTextField valueField = new JTextField(10);
        addButton.addActionListener(e -> {
            String key = keyField.getText();
            String value = valueField.getText();
            model.addRow(new Object[]{key, value});
            keyField.setText("");
            valueField.setText("");
        });

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(keyField);
        inputPanel.add(valueField);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(800, 600));
    }

    public java.util.List<KeyValue> getKeyValueList() {
        java.util.List<KeyValue> parameters = new ArrayList<>();
        int rowCount = this.model.getRowCount();
        for (int row = 0; row < rowCount; row++) {
            String value = model.getValueAt(row, 1).toString();
            String key = model.getValueAt(row, 0).toString();
            KeyValue keyValue = new KeyValue(key, value);
            parameters.add(keyValue);
        }
        return parameters;
    }

}
