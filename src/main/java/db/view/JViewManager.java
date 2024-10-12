package db.view;

import db.dao.DAOSelector;
import db.dao.LocatorDAO;
import db.model.Customer;
import db.view.panel.MainPanel;
import db.view.table.CustomerTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class JViewManager extends JFrame {

    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu configMenu;
    private JCheckBoxMenuItem useDatabaseItem;
    private JRadioButtonMenuItem dbSQLOption;
    private JRadioButtonMenuItem diskOption;
    private ButtonGroup storageOptionsGroup;

    public JViewManager(List<Customer> customers) {
        setTitle("Entity Manager");
        setSize(800, 600);
        CustomerTableModel tableModel = new CustomerTableModel();
        tableModel.setContent(customers);
        this.mainPanel = new MainPanel(Collections.singletonList(tableModel));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the menu bar
        menuBar = new JMenuBar();
        configMenu = new JMenu("Configuration");
        useDatabaseItem = new JCheckBoxMenuItem("Check Use Relational Database");
        configMenu.add(useDatabaseItem);
        dbSQLOption = new JRadioButtonMenuItem("dbSQL");
        diskOption = new JRadioButtonMenuItem("Disk");
        storageOptionsGroup = new ButtonGroup();
        storageOptionsGroup.add(dbSQLOption);
        storageOptionsGroup.add(diskOption);
        configMenu.add(dbSQLOption);
        configMenu.add(diskOption);
        menuBar.add(configMenu);
        this.setJMenuBar(menuBar);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public JCheckBoxMenuItem getUseDatabaseItem() {
        return useDatabaseItem;
    }

    public JRadioButtonMenuItem getDbSQLOption() {
        return dbSQLOption;
    }

    public JRadioButtonMenuItem getDiskOption() {
        return diskOption;
    }

}
