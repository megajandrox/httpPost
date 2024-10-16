package com.http.post.controller;

import com.http.post.controller.listener.*;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;
import commons.db.utils.bussiness.exceptions.SearchException;

import javax.swing.*;
import java.util.List;

public class RequestController {

    private ViewManager view;
    private static final int HEADER_TABLE = 0;
    private static final int PARAMETERS_TABLE = 1;
    //TODO: Implement add to favorites button
    //TODO: Implement show response status code, status message and headers
    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        try {
            URLFieldHelper.populateHttpRequest(this.view.getMainPanel().getUrlPanel().getUrlField());
        } catch (SearchException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(viewManager, "There was an error getting the URLs",
                    "Persistence Error", JOptionPane.ERROR_MESSAGE);
        }
        this.view.getMainPanel().getUrlPanel().getSendButton().addActionListener(new SendButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getClearButton().addActionListener(new ClearButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getSaveButton().addActionListener(new SaveButtonListener(this.view));
        this.view.getMainPanel().getUrlPanel().getUrlField().addActionListener(new UrlFieldListener(this.view));
        this.view.getMainPanel().getUrlPanel().getUrlField().addPopupMenuListener(new UrlFieldPopupListener(this.view));
        List<EntityJPanel> entityJPanels = this.view.getMainPanel().getEntityJPanels();

        entityJPanels.get(HEADER_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, PARAMETERS_TABLE));

        entityJPanels.get(HEADER_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, PARAMETERS_TABLE));

        this.view.getCreateDatabase().addActionListener(new DatabaseCreationListener(this.view));
    }
}

