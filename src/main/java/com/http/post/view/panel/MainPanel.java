package com.http.post.view.panel;

import com.http.post.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainPanel extends JPanel {

    private final RequestTopPanel requestTopPanel;
    private List<EntityJPanel> entityJPanels = new ArrayList<>();
    private TextPanel bodyPanel;
    private final TextPanel responsePanel;
    private JTabbedPane jTabbedPane;

    public MainPanel(List<EntityTableModel> tableModels) {
        super(new BorderLayout());
        this.requestTopPanel = new RequestTopPanel();
        requestTopPanel.setBorder(BorderFactory.createTitledBorder("URL"));
        add(requestTopPanel, BorderLayout.NORTH);
        this.jTabbedPane = new JTabbedPane();
        this.bodyPanel = new TextPanel("Body", true, BorderLayout.CENTER);
        jTabbedPane.add("Body", bodyPanel);
        tableModels.forEach(tableModel -> {
            RequestJPanel panel = new RequestJPanel(tableModel);
            entityJPanels.add(panel);
            jTabbedPane.add(panel, panel.getTableName());
        });
        add(jTabbedPane, BorderLayout.CENTER);
        this.responsePanel = new TextPanel("Response", false, BorderLayout.SOUTH);
        add(responsePanel, BorderLayout.SOUTH);
    }

    public List<EntityJPanel> getEntityJPanels() {
        return entityJPanels;
    }

    public TextPanel getBodyPanel() {
        return bodyPanel;
    }

    public RequestTopPanel getUrlPanel() {
        return requestTopPanel;
    }

    public TextPanel getResponsePanel() {
        return responsePanel;
    }

    public JTabbedPane getJTabbedPane() {
        return jTabbedPane;
    }
}
