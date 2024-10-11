package db.view.panel;

import db.model.Customer;
import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;

public class CustomerJPanel extends EntityJPanel<Customer> {

    private EntityTableModel<Customer> tableModel;
    private JTable table;
    private JButton addButton;
    private JButton removeButton;

    public CustomerJPanel(String tableName) {
        super(tableName);
    }

    public CustomerJPanel(EntityTableModel<Customer> tableModel) {
        this("Customer");
        setLayout(new BorderLayout());
        this.tableModel = tableModel;

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.addButton = new JButton("Add");
        add(addButton, BorderLayout.EAST);
        this.removeButton = new JButton("Remove");
        add(removeButton, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(800, 600));
    }

    public JButton getAddButton() {
        return addButton;
    }

    @Override
    public JButton getRemoveButton() {
        return removeButton;
    }

    @Override
    public EntityTableModel<Customer> getTableModel() {
        return this.tableModel;
    }

    public JTable getTable() {
        return table;
    }
}
