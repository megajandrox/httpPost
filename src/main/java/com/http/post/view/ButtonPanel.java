package com.http.post.view;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    public ButtonPanel() {
        setLayout(new FlowLayout());
        add(new ExecuteButton());
        add(new AddToFavoritesButton());
        add(new ClearButton());
    }
}
