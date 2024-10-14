package com.http.post.view.table;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityTableModel extends AbstractTableModel {

    protected final List<KeyValue> content = new ArrayList<>();
    private Class[] columnClasses = {};
    private String[]  columnNames = {};

    private String tableName;

    public EntityTableModel(Class[] columnClasses, String[] columnNames, String tableName) {
        this.columnNames = columnNames;
        this.columnClasses = columnClasses;
        this.tableName = tableName;
    }


    public void setContent(List<KeyValue> content) {
        this.content.clear();
        this.content.addAll(content);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return this.content.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.columnNames[column];
    }

    @Override
    public Class getColumnClass(int col) {
        return this.columnClasses[col];
    }

    public void addRow(KeyValue object) {
        this.content.add(object);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void removeRow(int selectedRow) {
        this.content.remove(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }

    public void removeAllRows() {
        this.content.clear();
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        KeyValue keyValue = this.content.get(rowIndex);
        switch (columnIndex) {
            case 0:
                keyValue.setKey((String) aValue);
                break;
            case 1:
                keyValue.setValue((String) aValue);
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public String getTableName() {
        return tableName;
    }

    public List<KeyValue> getContent() {
        return content;
    }

}