package com.http.post.view.table;

public class KeyValueTableModel extends EntityTableModel {

    public static final int KEY = 0;
    public static final int VALUE = 1;

    public KeyValueTableModel(String tableName) {
        super(new Class[]{String.class, String.class}, new String[]{"Key", "Value"}, tableName);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KeyValue item = this.content.get(rowIndex);
        switch (columnIndex) {
            case KEY:
                return item.getKey();
            case VALUE:
                return item.getValue();
            default:
                return "";
        }
    }
}
