package com.http.post.view.buttonPanel;

import javax.swing.*;

public class ClearButton extends JButton {

    public ClearButton() {
        super("Clear");
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("ClearButton clicked");
        });
    }

}
