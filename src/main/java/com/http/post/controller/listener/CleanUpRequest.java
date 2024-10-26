package com.http.post.controller.listener;

import com.http.post.view.ViewManager;

import static com.http.post.controller.listener.DeleteButtonListener.EMPTY;
import static com.http.post.controller.listener.DeleteButtonListener.FIRST_INDEX;

public interface CleanUpRequest {

    default void cleanUpRequestComponents(ViewManager view) {
        view.getMainPanel().getEntityJPanels().forEach(EntityJPanel -> EntityJPanel.getTableModel().removeAllRows());
        view.getMainPanel().getUrlPanel().getUrlField().setText(EMPTY);
        view.getMainPanel().getBodyPanel().getTextArea().setText(EMPTY);
        view.getMainPanel().getResponsePanel().getTextArea().setText(EMPTY);
        view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedIndex(FIRST_INDEX);
    }
}
