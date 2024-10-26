package com.http.post.controller.listener;

import com.http.post.controller.URLFieldHelper;
import com.http.post.repository.DAOType;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.SearchException;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbstractPersistListener implements ActionListener {

    private final String title;
    private final String message;
    private final DAOType daoType;
    private ViewManager viewManager;
    private JRadioButtonMenuItem radioButton;

    public AbstractPersistListener(ViewManager viewManager, JRadioButtonMenuItem radioButton, String title, String message, DAOType daoType) {
        this.radioButton = radioButton;
        this.viewManager = viewManager;
        this.title = title;
        this.message = message;
        this.daoType = daoType;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        performed(daoType);
    }

    private void performed(DAOType type) {
        if(this.radioButton.isSelected()) {
            try {
                Locator.getInstance().switchDAOType(type);
                URLFieldHelper.populateHttpRequest(this.viewManager.getSearchPanel().getUrlSearch());
            } catch (SearchException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(viewManager, title, message, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
