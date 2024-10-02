package com.http.post.view.requestPanel;

import javax.swing.*;
import java.awt.*;

public class BodyPanel extends JPanel {
    private JTextArea requestBody;
    private JTextArea response;

    public BodyPanel() {
        this.requestBody = new JTextArea(10, 40);
        this.response = new JTextArea(10, 40);
        this.response.setEditable(false);
        setLayout(new GridLayout(2, 1));
        add(createTextAreaPanel("Body", requestBody));
        add(createTextAreaPanel("Response", response));
    }

    private JPanel createTextAreaPanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    public JTextArea getRequestBody() {
        return requestBody;
    }

    public JTextArea getResponse() {
        return response;
    }
}
