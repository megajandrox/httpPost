package com.http.post.view.requestPanel;

import javax.swing.*;
import java.awt.*;

public class UrlPanel extends JPanel {
    private final JTextField urlField = new JTextField(50);
    private static final JComboBox<String> methodDropdown = new JComboBox<>(new String[] {"GET", "POST", "PUT", "DELETE"});

    public JTextField getUrlField() {
        return urlField;
    }

    public JComboBox<String> getMethodDropdown() {
        return methodDropdown;
    }

    public UrlPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("Method:"));
        add(methodDropdown);
        add(new JLabel("URL:"));
        add(urlField);
    }
}
