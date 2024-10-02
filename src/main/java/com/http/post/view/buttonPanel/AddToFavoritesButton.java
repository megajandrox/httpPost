package com.http.post.view.buttonPanel;

import javax.swing.*;

public class AddToFavoritesButton extends JButton {

    public AddToFavoritesButton() {
        super("AddToFavorites");
        initActionListener();
    }

    private void initActionListener() {
        this.addActionListener(e -> {
            System.out.println("AddToFavorites clicked");
        });
    }

}
