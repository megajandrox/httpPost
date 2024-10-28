package com.http.post.view.popup;

import com.http.post.controller.listener.URLSearchMouseListener;
import com.http.post.view.ViewManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchPopupComponent {

    public JTextField searchField;
    private DefaultListModel<SearchableItem> listModel;
    private JList<SearchableItem> resultList;
    private List<SearchableItem> data = new ArrayList<>();
    private JPopupMenu searchPopup;
    private SearchableItem selectedItem;
    private ViewManager view;

    public SearchPopupComponent(ViewManager view) {
        this.view = view;
        searchField = new JTextField(100);
        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        searchPopup = new JPopupMenu();
        searchPopup.setPreferredSize(new Dimension(200, 300));
        searchPopup.add(new JScrollPane(resultList));
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchField.getText();
                if (text.length() >= 3) {
                    new SearchTask().execute();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                new SearchTask().execute();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                new SearchTask().execute();
            }
        });

        resultList.addMouseListener(new URLSearchMouseListener(view));
    }

    public void addData(SearchableItem item) {
        this.data.add(item);
    }

    public void clearData() {
        this.data.clear();
    }

    private void showSearchResults() {
        String query = searchField.getText().toLowerCase();
        listModel.clear();

        for (SearchableItem item : data) {
            if (item.similarTo(new ItemURL(query))) {
                listModel.addElement(item);
            }
        }

        if (!listModel.isEmpty()) {
            resultList.setVisibleRowCount(Math.min(5, listModel.size()));
            searchPopup.show(searchField, 0, searchField.getHeight());
        } else {
            searchPopup.setVisible(false);
        }
        searchField.requestFocusInWindow();
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public SearchableItem getSelectedItem() {
        return selectedItem;
    }

    public static class ItemURL implements SearchableItem {
        private String url;

        public ItemURL(String url) {
            this.url = url;
        }

        @Override
        public String getSearchField() {
            return url;
        }

        @Override
        public String toString() {
            return url;
        }
    }

    public JList<SearchableItem> getResultList() {
        return resultList;
    }

    public JPopupMenu getSearchPopup() {
        return searchPopup;
    }

    private class SearchTask extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            showSearchResults();
            return null;
        }
    }
}
