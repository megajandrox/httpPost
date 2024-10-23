package com.http.post.controller.listener;

import com.http.post.controller.worker.JobExecutor;
import com.http.post.exceptions.InvalidMethodException;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.UpsertException;
import com.http.post.view.ViewManager;
import javax.swing.*;
import java.awt.event.ActionListener;

public class SaveButtonListener extends RequestHandler implements ActionListener, JobExecutor {

    public SaveButtonListener(ViewManager view) {
        super(view);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getSaveButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getSaveButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        try {
            Locator.getInstance().getRequestDAO().upsert(createRequest());
            JOptionPane.showMessageDialog(view, "Saved successfully",
                    "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (InvalidMethodException | UpsertException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(view, "Cannot save request",
                    "Save", JOptionPane.ERROR_MESSAGE);
        }
    }
}
