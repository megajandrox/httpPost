package com.http.post.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class UrlPanel extends JPanel {
    private final JTextField urlField = new JTextField(50);
    private static final JComboBox<String> methodDropdown = new JComboBox<>(new String[] {"GET", "POST", "PUT", "DELETE"});

    public UrlPanel() {
        setLayout(new FlowLayout());
        add(new JLabel("URL:"));
        urlField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onChangeURL();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onChangeURL();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onChangeURL();
            }

            private void onChangeURL() {
                String nuevoValor = urlField.getText();
                System.out.println("Nuevo valor: " + nuevoValor);
            }
        });
        add(urlField);
        add(new JLabel("Method:"));
        add(methodDropdown);
    }


}
