package db.view.table;


import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityTableModel<T> extends AbstractTableModel {

    protected final List<T> content = new ArrayList<>();
    private Class[] columnClasses = {};
    private String[]  columnNames = {};

    public EntityTableModel(Class[] columnClasses, String[] columnNames) {
        this.columnNames = columnNames;
        this.columnClasses = columnClasses;
    }

    public void addContent(List<T> content) {
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

    public void addRow(T object) {
        this.content.add(object);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    public void removeRow(int selectedRow) {
        this.content.remove(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }
}