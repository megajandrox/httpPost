package com.http.post.controller.listener;

import com.http.post.controller.URLFieldHelper;
import com.http.post.service.DBType;
import com.http.post.service.ServiceException;
import com.http.post.service.ServiceLocator;
import com.http.post.view.ViewManager;

import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class AbstractPersistListener implements ActionListener {

    private final String title;
    private final String message;
    private final DBType dbType;
    private ViewManager viewManager;
    private JRadioButtonMenuItem radioButton;

    public AbstractPersistListener(ViewManager viewManager, JRadioButtonMenuItem radioButton, String title, String message, DBType dbType) {
        this.radioButton = radioButton;
        this.viewManager = viewManager;
        this.title = title;
        this.message = message;
        this.dbType = dbType;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        performed(dbType);
    }

    private void performed(DBType type) {
        if(this.radioButton.isSelected()) {
            try {
                ServiceLocator.getInstance().switchDBType(type);
                URLFieldHelper.populateHttpRequest(this.viewManager.getSearchPanel().getSearchPopupComponent());
            } catch (ServiceException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(viewManager, title, message, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
