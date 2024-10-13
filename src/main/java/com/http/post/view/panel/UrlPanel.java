package com.http.post.view.panel;

import javax.swing.*;
import java.awt.*;

public class UrlPanel extends JPanel {

    private final JTextField urlField = new JTextField(50);
    private static final JComboBox<String> methodDropdown = new JComboBox<>(new String[] {"GET", "POST", "PUT", "DELETE"});

    public String getUrl() {
        return urlField.getText();
    }

    public String getMethod() {
        return methodDropdown.getSelectedIndex() == 0 ? "GET" : methodDropdown.getSelectedItem().toString();
    }

    public UrlPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("Method:"));
        add(methodDropdown);
        add(new JLabel("URL:"));
        add(urlField);
    }
}
