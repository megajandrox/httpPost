package com.http.post.view;

import javax.swing.*;
import java.awt.*;

public class BodyPanel extends JPanel {
    private JTextArea requestBodyArea;
    private JTextArea responseBodyArea;

    public BodyPanel(JTextArea requestBodyArea, JTextArea responseBodyArea) {
        this.requestBodyArea = requestBodyArea;
        this.responseBodyArea = responseBodyArea;
        setLayout(new GridLayout(2, 1));
        add(createTextAreaPanel("Request Body", requestBodyArea));
        add(createTextAreaPanel("Response Body", responseBodyArea));
    }

    private JPanel createTextAreaPanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }
}
