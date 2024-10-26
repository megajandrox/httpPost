package com.http.post.controller.listener;

import com.http.post.controller.worker.JobExecutor;
import com.http.post.repository.Locator;
import com.http.post.utils.bussiness.exceptions.DeletionException;
import com.http.post.view.ViewManager;
import com.http.post.view.model.RequestData;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.http.post.controller.URLFieldHelper.setRequestDataOnView;

public class DeleteButtonListener implements ActionListener, JobExecutor, CleanUpRequest {

    public static final String EMPTY = "";
    public static final int FIRST_INDEX = 0;
    private ViewManager view;

    public DeleteButtonListener(ViewManager view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }

    @Override
    public void enableButton() {
        this.view.getMainPanel().getUrlPanel().getDeleteButton().setEnabled(true);
    }

    @Override
    public void disableButton() {
        this.view.getMainPanel().getUrlPanel().getDeleteButton().setEnabled(false);
    }

    @Override
    public void actionPerform() throws Exception {
        Object selectedItem = this.view.getSearchPanel().getUrlSearch().getSelectedItem();
        if(selectedItem instanceof RequestData) {
            RequestData requestData = (RequestData) selectedItem;
            try {
                Locator.getInstance().getRequestDAO().delete(requestData.getId());
                JComboBox<RequestData> urlField = this.view.getSearchPanel().getUrlSearch();
                urlField.removeItem(requestData);
                if(urlField.getItemCount() > 0) {
                    urlField.setSelectedIndex(FIRST_INDEX);
                    setRequestDataOnView(urlField.getItemAt(FIRST_INDEX), this.view);
                } else {
                    cleanUpRequestComponents(this.view);
                }
            } catch (DeletionException ex) {
                System.err.println(ex.getMessage());
                JOptionPane.showMessageDialog(view, "Cannot delete request",
                        "Delete", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
