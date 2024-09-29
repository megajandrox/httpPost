package com.http.post.view;

import javax.swing.*;
import java.awt.*;

public class ParamsAndHeadersPanel extends JPanel {
    private JTable parametersTable;
    private JTable headersTable;

    public ParamsAndHeadersPanel(JTable parametersTable, JTable headersTable) {
        this.parametersTable = parametersTable;
        this.headersTable = headersTable;
        setLayout(new GridLayout(1, 2));
        add(createTablePanel("Parameters", parametersTable));
        add(createTablePanel("Headers", headersTable));
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
