package db.dao;

import db.model.Customer;

import java.util.Objects;

public class LocatorDAO {

    private static final LocatorDAO INSTANCE = new LocatorDAO();

    public static LocatorDAO getInstance() {
        return INSTANCE;
    }

    private DAO<Customer> customerDAO;

    private LocatorDAO() {
        switchDAOType(DAOSelector.H2);
    }

    public void switchDAOType(DAOSelector selector) {
        if (Objects.requireNonNull(selector) == DAOSelector.FILE) {
            customerDAO = new CustomerDAOFile();
        } else {
            customerDAO = new CustomerDAOH2();
        }
    }

    public DAO<Customer> getCustomerDAO() {
        return customerDAO;
    }
}
