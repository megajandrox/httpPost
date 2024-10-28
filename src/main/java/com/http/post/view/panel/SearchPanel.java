package com.http.post.view.panel;

import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import com.http.post.view.popup.SearchPopupComponent;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {

    private final JComboBox<RequestData> urlSearch = new JComboBox<>(new RequestData[] {new RequestData("", "GET", "")});
    private final SearchPopupComponent searchPopupComponent;
    private final ViewManager view;

    public SearchPanel(ViewManager view) {
        setLayout(new BorderLayout());
        this.view = view;
        JLabel searchLabel = new JLabel("Search:");
        urlSearch.setEditable(true);
        urlSearch.setPreferredSize(new Dimension(400, 25));
        // Add the label and text field to the panel
        add(searchLabel, BorderLayout.WEST);
        searchPopupComponent = new SearchPopupComponent(view);
        add(searchPopupComponent.getSearchField(), BorderLayout.CENTER);
    }

    public JComboBox<RequestData> getUrlSearch() {
        return urlSearch;
    }

    public SearchPopupComponent getSearchPopupComponent() {
        return searchPopupComponent;
    }
}
