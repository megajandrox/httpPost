package com.http.post.view;

import com.http.post.view.requestPanel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ViewManager extends JFrame {

    private MainPanel mainPanel;

    public ViewManager() {
        // Setting up the main frame
        setTitle("HTTP Post");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        this.mainPanel = new MainPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(mainPanel.getButtonPanel(), BorderLayout.SOUTH);
        this.pack();
    }

    public MainPanel getRequestPanel() {
        return mainPanel;
    }

}
