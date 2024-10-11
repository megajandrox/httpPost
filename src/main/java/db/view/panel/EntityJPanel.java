package db.view.panel;

import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.*;

public abstract class EntityJPanel<T> extends JPanel {

    private String tableName;

    public EntityJPanel(String tableName) {
        super(new BorderLayout());
        this.tableName = tableName;
    }

    public abstract JButton getAddButton();
    public abstract JButton getRemoveButton();
    public abstract JTable getTable();
    public abstract EntityTableModel getTableModel();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
