package com.http.post.controller;

import com.http.post.controller.listener.AddKeyValueItemListener;
import com.http.post.controller.listener.ClearButtonListener;
import com.http.post.controller.listener.RemoveKeyValueItemListener;
import com.http.post.controller.listener.SendButtonListener;
import com.http.post.view.ViewManager;
import com.http.post.view.panel.EntityJPanel;

import java.util.List;

public class RequestController {

    private ViewManager view;
    private static final int HEADER_TABLE = 0;
    private static final int PARAMETERS_TABLE = 1;
    //TODO: Implement add to favorites button
    //TODO: Implement message errors dialog and custom exceptions
    //TODO: Implement show response status code, status message and headers
    public RequestController(ViewManager viewManager) {
        this.view = viewManager;
        this.view.getMainPanel().getButtonPanel().getSendButton().addActionListener(new SendButtonListener(this.view));
        this.view.getMainPanel().getButtonPanel().getClearButton().addActionListener(new ClearButtonListener(this.view));
        List<EntityJPanel> entityJPanels = this.view.getMainPanel().getEntityJPanels();

        entityJPanels.get(HEADER_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getAddButton()
                .addActionListener(new AddKeyValueItemListener(this.view, PARAMETERS_TABLE));

        entityJPanels.get(HEADER_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, HEADER_TABLE));
        entityJPanels.get(PARAMETERS_TABLE).getRemoveButton()
                .addActionListener(new RemoveKeyValueItemListener(this.view, PARAMETERS_TABLE));
    }
}
