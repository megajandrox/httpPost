package com.http.post.controller.utils;

import com.http.post.view.ViewManager;
import com.http.post.view.panel.HandleBodyResponse;
import com.http.post.view.panel.HandleEmpty;

import java.awt.*;

import static com.http.post.controller.listener.DeleteButtonListener.EMPTY;
import static com.http.post.controller.listener.DeleteButtonListener.FIRST_INDEX;

public interface CleanUpRequest {

    default void cleanUpRequestComponents(ViewManager view) {
        view.setSelectedRequestData(null);
        view.getMainPanel().getEntityJPanels().forEach(EntityJPanel -> EntityJPanel.getTableModel().removeAllRows());
        view.getMainPanel().getUrlPanel().getUrlField().setText(EMPTY);
        view.getMainPanel().getBodyPanel().getTextArea().setText(EMPTY);
        view.getMainPanel().getJTabbedPane().setSelectedIndex(FIRST_INDEX);
        view.getMainPanel().getJTabbedPaneImg().setSelectedIndex(FIRST_INDEX);
        for (Component component : view.getMainPanel().getJTabbedPaneImg().getComponents()) {
            HandleEmpty handleEmpty = (HandleEmpty) component;
            handleEmpty.empty();
        }
        view.getMainPanel().getUrlPanel().getMethodDropdown().setSelectedIndex(FIRST_INDEX);
    }
}
