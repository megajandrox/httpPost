package com.http.post.view.buttonPanel;

import com.http.post.view.requestPanel.MainPanel;

import javax.swing.*;

public class ExecuteButton extends JButton {

    private MainPanel mainPanel;

    public ExecuteButton(MainPanel mainPanel) {
        super("Execute");
        this.mainPanel = mainPanel;
    }

    public MainPanel getRequestPanel() {
        return mainPanel;
    }
}
