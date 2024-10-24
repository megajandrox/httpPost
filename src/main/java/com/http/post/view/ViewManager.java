package com.http.post.view;

import com.http.post.view.model.RequestData;
import com.http.post.view.panel.MainPanel;
import com.http.post.view.table.KeyValueTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ViewManager extends JFrame {

    private final JTabbedPane jTabbedPane;
    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu configMenu;
    private JCheckBoxMenuItem createDatabase;
    private JRadioButtonMenuItem dbSQLOption;
    private JRadioButtonMenuItem diskOption;
    private ButtonGroup storageOptionsGroup;
    private final JComboBox<RequestData> urlSearch = new JComboBox<>(new RequestData[] {new RequestData("", "GET", "")});

    public ViewManager() {
        // Setting up the main frame
        setTitle("HTTP Post");
        setSize(800, 600);
        this.urlSearch.setEditable(true);
        this.urlSearch.setPreferredSize(new Dimension(400, 25));
        this.jTabbedPane = new JTabbedPane();
        KeyValueTableModel headerTableModel = new KeyValueTableModel("Header");
        KeyValueTableModel parameterTableModel = new KeyValueTableModel("Parameter");
        this.mainPanel = new MainPanel(Arrays.asList(headerTableModel, parameterTableModel));
        jTabbedPane.add("New Request", mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().add(jTabbedPane, BorderLayout.CENTER);
        getContentPane().add(urlSearch, BorderLayout.NORTH);
        // Create the menu bar
        menuBar = new JMenuBar();
        configMenu = new JMenu("Settings");
        createDatabase = new JCheckBoxMenuItem("Create Database");
        configMenu.add(createDatabase);

        dbSQLOption = new JRadioButtonMenuItem("dbSQL");
        diskOption = new JRadioButtonMenuItem("Disk");
        storageOptionsGroup = new ButtonGroup();
        storageOptionsGroup.add(dbSQLOption);
        storageOptionsGroup.add(diskOption);
        configMenu.add(dbSQLOption);
        configMenu.add(diskOption);
        menuBar.add(configMenu);
        this.setJMenuBar(menuBar);

        pack();
        setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JCheckBoxMenuItem getCreateDatabase() {
        return createDatabase;
    }

    public ButtonGroup getStorageOptionsGroup() {
        return storageOptionsGroup;
    }

    public JRadioButtonMenuItem getDbSQLOption() {
        return dbSQLOption;
    }

    public JRadioButtonMenuItem getDiskOption() {
        return diskOption;
    }

    public JComboBox<RequestData> getUrlSearch() {
        return urlSearch;
    }
}
