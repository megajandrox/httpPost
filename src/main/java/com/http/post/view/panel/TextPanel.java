package com.http.post.view.panel;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextArea textArea;

    public TextPanel(String name, Boolean editable, String layout) {
        setLayout(new BorderLayout());
        this.textArea = new JTextArea(10, 40);
        this.textArea.setEditable(editable);
        add(createTextAreaPanel(name, textArea, layout), BorderLayout.CENTER);
    }

    private JPanel createTextAreaPanel(String title, JTextArea textArea, String layout) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea), layout);
        return panel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

}
