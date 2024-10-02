package com.http.post.view.buttonPanel;

import com.http.post.view.requestPanel.MainPanel;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private AddParamButton addParamButton;
    private AddHeaderButton addHeaderButton;
    private ExecuteButton executeButton;

    public ButtonPanel(MainPanel mainPanel) {
        super(new FlowLayout());
        this.addParamButton = new AddParamButton(mainPanel, "Add Parameter");
        this.addHeaderButton = new AddHeaderButton(mainPanel, "Add Header");
        this.add(this.addParamButton);
        this.add(this.addHeaderButton);
        this.add(new AddToFavoritesButton());
        this.add(new ClearButton());
        this.executeButton = new ExecuteButton(mainPanel);
        this.add(this.executeButton);
    }

    public AddParamButton getAddParamButton() {
        return addParamButton;
    }

    public AddHeaderButton getAddHeaderButton() {
        return addHeaderButton;
    }

    public ExecuteButton getExecuteButton() {
        return executeButton;
    }
}
