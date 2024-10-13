package com.http.post.view.table;

import db.model.Customer;
import db.view.table.EntityTableModel;

public class KeyValueTableModel extends EntityTableModel {

    public static final int KEY = 0;
    public static final int VALUE = 1;

    public KeyValueTableModel(String tableName) {
        super(new Class[]{String.class, String.class}, new String[]{"Key", "Value"}, tableName);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer item = (Customer) content.get(rowIndex);
        switch (columnIndex) {
            case KEY:
                return item.getUsername();
            case VALUE:
                return item.getEmail();
            default:
                return "";
        }
    }
}
