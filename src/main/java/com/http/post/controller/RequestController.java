package com.http.post.controller;

import com.http.post.controller.listener.*;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.ButtonPanel;
import com.http.post.view.panel.EntityJPanel;

import java.util.List;

public class RequestController {

    private ViewManager view;
    private static final int HEADER_TABLE = 0;
    private static final int PARAMETERS_TABLE = 1;
    //TODO: Implement add to favorites button
    //TODO: Implement show response status code, status message and headers
    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        ButtonPanel buttonPanel = this.view.getMainPanel().getButtonPanel();
        buttonPanel.getSendButton().addActionListener(new SendButtonListener(this.view));
        buttonPanel.getClearButton().addActionListener(new ClearButtonListener(this.view));
        buttonPanel.getSaveButton().addActionListener(new SaveButtonListener(this.view));
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
