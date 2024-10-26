package com.http.post.view.panel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class RequestTopPanel extends JPanel {

    private final TextField urlField = new TextField();

    private static final JComboBox<String> methodDropdown = new JComboBox<>(new String[] {"GET", "POST", "PUT", "DELETE"});
    private final JButton sendButton;
    private final JButton deleteButton;
    private final JButton updateButton;
    private final JButton addButton;
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
        this.addButton = new JButton("Add");
        this.add(addButton);
        this.updateButton = new JButton("Update");
        this.add(updateButton);
        this.favoriteButton = new JButton("Favorite");
        this.add(favoriteButton);
        this.deleteButton = new JButton("Delete");
        this.add(deleteButton);
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

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getFavoriteButton() {
        return favoriteButton;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
