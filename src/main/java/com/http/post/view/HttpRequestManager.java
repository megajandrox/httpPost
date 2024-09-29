package com.http.post.view;

import javax.swing.*;
import java.awt.*;

public class HttpRequestManager extends JFrame {

    public HttpRequestManager() {
        // Setting up the main frame
        setTitle("HTTP Request Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize components
        JPanel requestPanel = new RequestPanel(new BorderLayout());
        ButtonPanel buttonPanel = new ButtonPanel();
        JTabbedPane responseTabs = new ResponseTabs();
        add(requestPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(responseTabs, BorderLayout.EAST);
    }

}
