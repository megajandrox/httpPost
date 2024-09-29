package com.http.post.view;

import javax.swing.*;

public class ExecuteButton extends JButton {

    //TODO HASHSET OFF VARIABLES to handle events when the user clicks the button

    public ExecuteButton() {
        super("Execute");
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("executeButton clicked");
        });
    }
}
