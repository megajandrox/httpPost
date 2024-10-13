package com.http.post.view.panel;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private final JButton sendButton;
    private final JButton clearButton;
    private final JButton saveButton;
    private final JButton favoriteButton;

    public ButtonPanel() {
        setLayout(new GridLayout(4, 1));
        this.sendButton = new JButton("Send");
        this.clearButton = new JButton("Clear");
        this.saveButton = new JButton("Save");
        this.favoriteButton = new JButton("Favorite");
        this.add(sendButton);
        this.add(clearButton);
        this.add(saveButton);
        this.add(favoriteButton);
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getFavoriteButton() {
        return favoriteButton;
    }
}
