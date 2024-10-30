package com.http.post.view.panel;

import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.popup.SearchPopupComponent;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private final SearchPopupComponent searchPopupComponent;
    private final ViewManager view;

    public SearchPanel(ViewManager view) {
        setLayout(new BorderLayout());
        this.view = view;
        JLabel searchLabel = new JLabel("Search:");
        // Add the label and text field to the panel
        add(searchLabel, BorderLayout.WEST);
        searchPopupComponent = new SearchPopupComponent(view);
        add(searchPopupComponent.getSearchField(), BorderLayout.CENTER);
    }

    public SearchPopupComponent getSearchPopupComponent() {
        return searchPopupComponent;
    }
}
