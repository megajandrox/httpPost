package db.view;

import db.model.Customer;
import db.view.panel.MainPanel;
import db.view.table.CustomerTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class JViewManager extends JFrame {

    private MainPanel mainPanel;

    public JViewManager(List<Customer> customers) {
        setTitle("Entity Manager");
        setSize(800, 600);
        CustomerTableModel tableModel = new CustomerTableModel();
        tableModel.addContent(customers);
        this.mainPanel = new MainPanel(Collections.singletonList(tableModel));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

}
