package db;

import commons.db.utils.bussiness.exceptions.SearchException;
import db.controller.CustomerController;
import db.dao.DAO;
import db.dao.CustomerDAOH2;
import db.model.Customer;
import db.view.JViewManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //TableManager.createUserTable();
        SwingUtilities.invokeLater(() -> {
            DAO<Customer> userDAO = new CustomerDAOH2();
            try {
                JViewManager viewManager = new JViewManager(userDAO.getAll());
                new CustomerController(userDAO, viewManager);
                viewManager.setVisible(true);
            } catch (SearchException e) {
                throw new RuntimeException(e);
            }
        });
        //TableManager.dropUserTable();
    }
}
