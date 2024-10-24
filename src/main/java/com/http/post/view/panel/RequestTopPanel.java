package com.http.post.view.panel;

import com.http.post.view.model.RequestData;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RequestTopPanel extends JPanel {

    private final TextField urlField = new TextField();

    private static final JComboBox<String> methodDropdown = new JComboBox<>(new String[] {"GET", "POST", "PUT", "DELETE"});
    private final JButton sendButton;
    private final JButton clearButton;
    private final JButton saveButton;
    private final JButton favoriteButton;

    public String getUrl() {
        return Objects.requireNonNull(urlField.getText());
    }

    public String getMethod() {
        return methodDropdown.getSelectedIndex() == 0 ? "GET" : methodDropdown.getSelectedItem().toString();
    }

    public RequestTopPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("Method:"));
        add(methodDropdown);
        add(new JLabel("URL:"));
        this.urlField.setEditable(true);
        this.urlField.setPreferredSize(new Dimension(400, 25));
        add(urlField);
        this.sendButton = new JButton("Send");
        this.add(sendButton);
        this.saveButton = new JButton("Save");
        this.add(saveButton);
        this.favoriteButton = new JButton("Favorite");
        this.add(favoriteButton);
        this.clearButton = new JButton("Clear");
        this.add(clearButton);
    }

    public TextField getUrlField() {
        return urlField;
    }

    public JComboBox<String> getMethodDropdown() {
        return methodDropdown;
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
