package db.view.panel;

import db.model.Customer;
import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainPanelFake extends JPanel {

    private List<EntityJPanel> entityJPanels = new ArrayList<>();

    public MainPanelFake(List<EntityTableModel> tableModels) {
        super(new java.awt.BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        JPanel bodyPanel = new JPanel(new BorderLayout());
        bodyPanel.add(new JLabel("Body content goes here."), BorderLayout.CENTER);
        jTabbedPane.add("Body", bodyPanel);
        tableModels.forEach(tableModel -> {
            EntityJPanel<Customer> panel = new CustomerJPanel(tableModel);
            entityJPanels.add(panel);
            jTabbedPane.add(panel, panel.getTableName());
        });
        add(jTabbedPane, BorderLayout.CENTER);
    }

    public List<EntityJPanel> getEntityJPanels() {
        return entityJPanels;
    }
}
