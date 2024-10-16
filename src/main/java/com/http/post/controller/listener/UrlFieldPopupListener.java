package com.http.post.controller.listener;

import com.http.post.controller.RequestController;
import com.http.post.view.ViewManager;
import commons.db.utils.bussiness.exceptions.SearchException;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import static com.http.post.controller.URLFieldHelper.populateHttpRequest;

public class UrlFieldPopupListener implements PopupMenuListener {

    private ViewManager view;

    public UrlFieldPopupListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        try {
            populateHttpRequest(this.view.getMainPanel().getUrlPanel().getUrlField());
        } catch (SearchException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(this.view, "There was an error getting the URLs",
                    "Persistence Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        System.out.println("Arrow button clicked (ComboBox closed)!");
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        System.out.println("Arrow button clicked (ComboBox closed)!");
    }
}
