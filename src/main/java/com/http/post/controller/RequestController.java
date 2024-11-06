package com.http.post.controller;

import com.http.post.controller.listener.*;
import com.http.post.service.ServiceException;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import javax.swing.*;
import java.util.List;

public class RequestController {

    private ViewManager view;
    private static final int HEADER_TABLE = 0;
    private static final int PARAMETERS_TABLE = 1;

    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        try {
            URLFieldHelper.populateHttpRequest(this.view.getSearchPanel().getSearchPopupComponent());
        } catch (ServiceException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(viewManager, "There was an error getting the URLs",
                    "Persistence Error", JOptionPane.ERROR_MESSAGE);
        }
        this.view.getMainPanel().getUrlPanel().getSendButton().addActionListener(new SendButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getAddButton().addActionListener(new AddButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getUpdateButton().addActionListener(new UpdateButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getDeleteButton().addActionListener(new DeleteButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getFavoriteButton().addActionListener(new AddFavoriteButtonListener(this.view));

        List<EntityJPanel> entityJPanels = this.view.getMainPanel().getEntityJPanels();
        entityJPanels.get(HEADER_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, PARAMETERS_TABLE));
        entityJPanels.get(HEADER_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, PARAMETERS_TABLE));

        viewManager.getDbSQLOption().addActionListener(new DbSQLOptionListener(this.view));
        viewManager.getDiskOption().addActionListener(new DiskOptionListener(this.view));
    }
}

