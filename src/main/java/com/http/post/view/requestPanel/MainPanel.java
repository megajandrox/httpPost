package com.http.post.view.requestPanel;

import com.http.post.view.buttonPanel.ButtonPanel;
import com.http.post.view.table.CustomJTable;
import com.http.post.view.table.KeyValue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainPanel extends JPanel {

    private CustomJTable parametersTable;
    private CustomJTable headersTable;
    private JTextArea requestBodyArea;
    private JTextArea responseBodyArea;
    private UrlPanel urlPanel = new UrlPanel();
    private ButtonPanel buttonPanel;

    public MainPanel(BorderLayout bl) {
        super(bl);
        this.buttonPanel = new ButtonPanel(this);
        add(urlPanel, BorderLayout.NORTH);
        BodyPanel bodyPanel = new BodyPanel();
        requestBodyArea = bodyPanel.getRequestBody();
        responseBodyArea = bodyPanel.getResponse();
        parametersTable = new CustomJTable(buttonPanel.getAddParamButton());
        headersTable = new CustomJTable(buttonPanel.getAddHeaderButton());
        JTabbedPane paramsAndHeadersPanel = new ParamsAndHeadersPanel(parametersTable, headersTable, bodyPanel);
        add(paramsAndHeadersPanel, BorderLayout.CENTER);

        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // Add buttons to the panel
        buttonPanel.add(buttonPanel.getAddParamButton());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(buttonPanel.getAddHeaderButton());
        add(buttonPanel, BorderLayout.EAST);
    }

    public void setResponseBodyArea(String responseBody) {
        this.responseBodyArea.setText(responseBody);
    }

    public String getURL() {
        return urlPanel.getUrlField().getText();
    }

    public String getMethodSelected() {
        return (String) urlPanel.getMethodDropdown().getSelectedItem();
    }

    public JTextArea getRequestBodyArea() {
        return requestBodyArea;
    }

    public List<KeyValue> getParameters() {
        return this.parametersTable.getKeyValueList().stream().map(v -> v).collect(Collectors.toList());
    }

    public List<KeyValue> getHeaders() {
        return this.headersTable.getKeyValueList().stream().map(v -> v).collect(Collectors.toList());
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public CustomJTable getHeadersTable() {
        return headersTable;
    }
}
