package com.http.post.view;

import javax.swing.*;

public class ResponseTabs extends JTabbedPane {

    public ResponseTabs() {
        addTab("Visualizer", new JComboBox<>(new String[] {"XML", "JSON", "IMAGE"}));
        addTab("HTTP Protocol",  new JScrollPane( new JTextArea(10, 40)));
    }
}
