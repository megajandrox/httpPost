package com.http.post.view;

import com.http.post.view.model.RequestData;
import com.http.post.view.panel.MainPanel;
import com.http.post.view.panel.SearchPanel;
import com.http.post.view.table.KeyValueTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ViewManager extends JFrame {

    private final SearchPanel searchPanel;
    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu configMenu;
    private JCheckBoxMenuItem createDatabase;
    private JRadioButtonMenuItem dbSQLOption;
    private JRadioButtonMenuItem diskOption;
    private ButtonGroup storageOptionsGroup;
    private RequestData selectedRequestData;

    public ViewManager() {
        setTitle("HTTP Post");
        setSize(800, 600);
        KeyValueTableModel headerTableModel = new KeyValueTableModel("Header");
        KeyValueTableModel parameterTableModel = new KeyValueTableModel("Parameter");
        mainPanel = new MainPanel(Arrays.asList(headerTableModel, parameterTableModel));
        searchPanel = new SearchPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(searchPanel, BorderLayout.NORTH);
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

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public RequestData getSelectedRequestData() {
        return selectedRequestData;
    }

    public void setSelectedRequestData(RequestData selectedRequestData) {
        this.selectedRequestData = selectedRequestData;
    }
}
