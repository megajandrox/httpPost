package com.http.post.view;

import com.http.post.view.panel.MainPanel;
import com.http.post.view.table.KeyValueTableModel;
import db.view.panel.MainPanelFake;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ViewManager extends JFrame {

    private MainPanel mainPanel;

    public ViewManager() {
        // Setting up the main frame
        setTitle("HTTP Post");
        setSize(800, 600);
        KeyValueTableModel headerTableModel = new KeyValueTableModel("Header");
        KeyValueTableModel parameterTableModel = new KeyValueTableModel("Parameter");
        this.mainPanel = new MainPanel(Arrays.asList(headerTableModel, parameterTableModel));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
