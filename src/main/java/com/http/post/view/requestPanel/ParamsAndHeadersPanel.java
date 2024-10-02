package com.http.post.view.requestPanel;

import javax.swing.*;
import java.awt.*;

public class ParamsAndHeadersPanel extends JTabbedPane {
    private JTable parametersTable;
    private JTable headersTable;

    public ParamsAndHeadersPanel(JTable parametersTable, JTable headersTable, BodyPanel bodyArea) {
        this.parametersTable = parametersTable;
        this.headersTable = headersTable;
        addTab("Body", bodyArea);
        addTab("Parameters", createTablePanel("Parameters", parametersTable));
        addTab("Headers",  createTablePanel("Headers", headersTable));
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
