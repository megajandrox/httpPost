package db;

import commons.db.utils.bussiness.exceptions.SearchException;
import db.controller.CustomerController;
import db.dao.DAO;
import db.dao.LocatorDAO;
import db.model.Customer;
import db.view.JViewManager;

import javax.swing.*;

public class MainFake {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
            try {
                JViewManager viewManager = new JViewManager(customerDAO.getAll());
                new CustomerController(viewManager);
                viewManager.setVisible(true);
            } catch (SearchException e) {
                e.printStackTrace();
                TableManager.createCustomerTable();
            }
        });
    }
}
