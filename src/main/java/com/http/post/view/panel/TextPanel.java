package com.http.post.view.panel;

import com.http.post.utils.json.JsonFormatter;
import com.http.post.utils.json.JsonFormatterException;
import com.http.post.utils.xml.XmlFormatter;
import com.http.post.utils.xml.XmlFormatterException;

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

    public void setBodyText(String body, String contentType) {
        try {
            switch (contentType.split(";")[0].trim()) {
                case "application/json":
                    textArea.setText(JsonFormatter.format(body));
                    break;
                case "application/xml":
                    textArea.setText(XmlFormatter.format(body));
                    break;
                case "image/png":
                    textArea.setText(body);
                    //TODO display image and check all type of content type related to images
                    break;
                default:
                    textArea.setText(body);
                    break;
            }
        } catch (JsonFormatterException | XmlFormatterException e) {
            throw new RuntimeException(e);
        }
    }
}
