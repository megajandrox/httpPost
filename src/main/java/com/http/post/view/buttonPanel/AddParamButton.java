package com.http.post.view.buttonPanel;

import com.http.post.view.requestPanel.MainPanel;

import javax.swing.*;

public class AddParamButton extends JButton {

    private MainPanel mainPanel;

    public AddParamButton(MainPanel mainPanel, String title) {
        super(title);
        this.mainPanel = mainPanel;
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Parameters: ");
        });
    }
}
