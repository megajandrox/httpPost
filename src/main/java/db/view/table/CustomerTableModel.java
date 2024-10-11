package db.view.table;

import db.model.Customer;

public class CustomerTableModel extends EntityTableModel {

    public static final int USERNAME = 0;
    public static final int EMAIL = 1;

    public CustomerTableModel() {
        super(new Class[]{String.class, String.class}, new String[]{"Username", "Email"});
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer item = (Customer) content.get(rowIndex);
        switch (columnIndex) {
            case USERNAME:
                return item.getUsername();
            case EMAIL:
                return item.getEmail();
            default:
                return "";
        }
    }
}
