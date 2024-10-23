package com.http.post.controller.listener;

import com.http.post.controller.worker.JobExecutor;
import com.http.post.repository.TableManager;
import com.http.post.view.ViewManager;
import com.http.post.utils.exceptions.DDLActionException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatabaseCreationListener  implements ActionListener, JobExecutor {

    private final ViewManager viewManager;

    public DatabaseCreationListener(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {}

    @Override
    public void disableButton() {}

    @Override
    public void actionPerform() throws Exception {
        if (viewManager.getCreateDatabase().isSelected()) {
            try {
                TableManager.createRequestTable();
                JOptionPane.showMessageDialog(viewManager, "Database created successfully",
                        "Create Database", JOptionPane.INFORMATION_MESSAGE);
            } catch (DDLActionException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(viewManager, "Cannot create Database",
                        "Create Database", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}