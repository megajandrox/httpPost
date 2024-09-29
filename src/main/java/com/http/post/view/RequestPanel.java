package com.http.post.view;

import javax.swing.*;
import java.awt.*;

public class RequestPanel extends JPanel {

    private JTable parametersTable;
    private JTable headersTable;
    private JTextArea requestBodyArea;
    private JTextArea responseBodyArea;

    public RequestPanel(BorderLayout layout) {
        super(layout);
        JPanel urlPanel = new UrlPanel();
        this.add(urlPanel, BorderLayout.NORTH);
        parametersTable = createTable(new String[] {"Parameter", "Value"});
        headersTable = createTable(new String[] {"Header", "Value"});

        JPanel paramsAndHeadersPanel = new ParamsAndHeadersPanel(parametersTable, headersTable);
        this.add(paramsAndHeadersPanel, BorderLayout.CENTER);

        requestBodyArea = new JTextArea(5, 40);
        responseBodyArea = new JTextArea(5, 40);
        JPanel bodyPanel = new BodyPanel(requestBodyArea, responseBodyArea);
        this.add(bodyPanel, BorderLayout.SOUTH);
    }

    private JTable createTable(String[] columnNames) {
        Object[][] data = {};
        return new JTable(data, columnNames);
    }
}
