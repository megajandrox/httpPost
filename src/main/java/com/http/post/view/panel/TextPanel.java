package com.http.post.view.panel;

import com.http.post.utils.formatter.ContextFormatter;
import com.http.post.utils.formatter.FormatterException;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel implements Responsible {

    private final JTextArea textArea;
    private JPanel panel;

    public TextPanel(String name, Boolean editable, String layout) {
        setLayout(new BorderLayout());
        this.textArea = new JTextArea(10, 40);
        this.textArea.setEditable(editable);
        add(createTextAreaPanel(name, textArea, layout), BorderLayout.CENTER);
    }

    private JPanel createTextAreaPanel(String title, JTextArea textArea, String layout) {
        this.panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea), layout);
        return panel;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    @Override
    public void setBody(String body, String contentType) {
        try {
            ContextFormatter contextFormatter = new ContextFormatter(contentType);
            textArea.setText(contextFormatter.format(body));
        } catch (FormatterException e) {
            throw new IllegalStateException(e);
        }
    }
}
