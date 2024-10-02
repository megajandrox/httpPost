package com.http.post.view.buttonPanel;

import com.http.post.view.requestPanel.MainPanel;

import javax.swing.*;

public class AddHeaderButton extends JButton {

    private MainPanel mainPanel;

    public AddHeaderButton(MainPanel mainPanel, String title) {
        super(title);
        this.mainPanel = mainPanel;
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("Headers: ");
        });
    }
}
