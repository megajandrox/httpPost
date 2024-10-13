package com.http.post.view.panel;

import db.view.panel.EntityJPanel;
import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainPanel extends JPanel {

    private final UrlPanel urlPanel;
    private List<EntityJPanel> entityJPanels = new ArrayList<>();
    private TextPanel bodyPanel;
    private final TextPanel responsePanel;
    private ButtonPanel buttonPanel;

    public MainPanel(List<EntityTableModel> tableModels) {
        super(new BorderLayout());
        this.urlPanel = new UrlPanel();
        urlPanel.setBorder(BorderFactory.createTitledBorder("URL"));
        add(urlPanel, BorderLayout.NORTH);
        JTabbedPane jTabbedPane = new JTabbedPane();
        this.bodyPanel = new TextPanel("Body", true, BorderLayout.CENTER);
        jTabbedPane.add("Body", bodyPanel);
        tableModels.forEach(tableModel -> {
            EntityJPanel panel = new RequestJPanel(tableModel);
            entityJPanels.add(panel);
            jTabbedPane.add(panel, panel.getTableName());
        });
        add(jTabbedPane, BorderLayout.CENTER);
        this.responsePanel = new TextPanel("Response", false, BorderLayout.SOUTH);
        add(responsePanel, BorderLayout.SOUTH);
        this.buttonPanel = new ButtonPanel();
        add(buttonPanel, BorderLayout.EAST);
    }

    public List<EntityJPanel> getEntityJPanels() {
        return entityJPanels;
    }

    public TextPanel getBodyPanel() {
        return bodyPanel;
    }

    public UrlPanel getUrlPanel() {
        return urlPanel;
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public TextPanel getResponsePanel() {
        return responsePanel;
    }
}
