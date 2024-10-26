package com.http.post.controller.listener;

import com.http.post.controller.worker.JobExecutor;
import com.http.post.view.ViewManager;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import com.http.post.utils.bussiness.exceptions.SearchException;
import java.awt.event.ActionEvent;
import static com.http.post.controller.URLFieldHelper.populateHttpRequest;

public class UrlFieldPopupListener implements PopupMenuListener, JobExecutor {

    public static final int BODY_TAB = 0;
    private ViewManager view;

    public UrlFieldPopupListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        execute();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}

    @Override
    public void enableButton() { }

    @Override
    public void disableButton() { }

    @Override
    public void actionPerform() throws Exception {
        try {
            populateHttpRequest(this.view.getSearchPanel().getUrlSearch());
            this.view.getMainPanel().getJTabbedPane().setSelectedIndex(BODY_TAB);
        } catch (SearchException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(this.view, "There was an error getting the URLs",
                    "Persistence Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
