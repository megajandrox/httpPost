package com.http.post.view.panel;

import com.http.post.view.model.RequestData;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private final JComboBox<RequestData> urlSearch = new JComboBox<>(new RequestData[] {new RequestData("", "GET", "")});

    public SearchPanel() {
        setLayout(new BorderLayout());
        JLabel searchLabel = new JLabel("Search:");
        urlSearch.setEditable(true);
        urlSearch.setPreferredSize(new Dimension(400, 25));
        // Add the label and text field to the panel
        add(searchLabel, BorderLayout.WEST);
        add(urlSearch, BorderLayout.CENTER);
    }

    public JComboBox<RequestData> getUrlSearch() {
        return urlSearch;
    }
}
