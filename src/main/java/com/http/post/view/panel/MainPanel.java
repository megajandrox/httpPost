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
    private JTabbedPane jTabbedPane;
    private JTabbedPane jTabbedPaneImg;

    public MainPanel(List<EntityTableModel> tableModels) {
        super(new BorderLayout());
        this.requestTopPanel = new RequestTopPanel();
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
        this.jTabbedPaneImg = new JTabbedPane();
        jTabbedPaneImg.add("Response Body", new TextPanel("Response", false, BorderLayout.SOUTH));
        jTabbedPaneImg.add("Image", new ImagePanel("Image", false, BorderLayout.SOUTH));
        jTabbedPaneImg.add("Response Protocol", new ProtocolTextPanel("Protocol", false, BorderLayout.SOUTH));
        add(jTabbedPaneImg, BorderLayout.SOUTH);
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

    public JTabbedPane getJTabbedPane() {
        return jTabbedPane;
    }

    public JTabbedPane getJTabbedPaneImg() {
        return jTabbedPaneImg;
    }
}
