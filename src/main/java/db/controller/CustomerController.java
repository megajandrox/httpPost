package db.controller;

import commons.db.utils.bussiness.exceptions.CreateException;
import commons.db.utils.bussiness.exceptions.DeletionException;
import commons.db.utils.bussiness.exceptions.SearchException;
import db.controller.exception.RemoveUserException;
import db.controller.exception.SaveUserException;
import db.dao.DAO;
import db.dao.DAOSelector;
import db.dao.LocatorDAO;
import db.model.Customer;
import db.view.panel.EntityJPanel;
import db.view.JViewManager;
import db.view.table.EntityTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static db.view.table.CustomerTableModel.EMAIL;
import static db.view.table.CustomerTableModel.USERNAME;

public class CustomerController {

    public static final int CUSTOMER_PANEL = 0;
    private final JViewManager viewManager;

    public CustomerController(JViewManager viewManager) {
        this.viewManager = viewManager;
        EntityJPanel entityJPanel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL);
        entityJPanel.getAddButton().addActionListener(new CustomerController.NewCustomerListener());
        entityJPanel.getRemoveButton().addActionListener(new CustomerController.RemoveCustomerListener());
        viewManager.getUseDatabaseItem().addActionListener(new CustomerController.UseDatabaseListener());
        viewManager.getDbSQLOption().addActionListener(new CustomerController.DbSQLOptionListener());
        viewManager.getDiskOption().addActionListener(new CustomerController.DiskOptionListener());
    }

    private void saveCustomer(String username, String email) throws SaveUserException {
        try {
            DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
            customerDAO.create(new Customer(username, email));
        } catch (CreateException e) {
            throw new SaveUserException(e);
        }
    }

    public void removeCustomer(String username, String email) throws RemoveUserException {
        try {
            DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
            customerDAO.delete(new Customer(username,email));
        } catch (DeletionException e) {
            throw new RemoveUserException(e);
        }
    }

    class NewCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RandomCustomerGenerator randomUsernameGenerator = new RandomCustomerGenerator();
            String username = randomUsernameGenerator.getRandomUsername();
            String email = randomUsernameGenerator.getRandomEmail();
            try {
                Customer customer = new Customer(username, email);
                viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL).getTableModel().addRow(customer);
                saveCustomer(username, email);
                JOptionPane.showMessageDialog(viewManager, "Customer added successfully",
                        "Add Customer", JOptionPane.INFORMATION_MESSAGE);
            } catch (SaveUserException e1) {
                System.err.println(e1.getMessage());
                JOptionPane.showMessageDialog(viewManager, "Cannot add customer",
                        "Add Customer", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class RemoveCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            EntityJPanel entityJPanel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL);
            int selectedRow = entityJPanel.getTable().getSelectedRow();
            if (selectedRow != -1) {
                try {
                    String username = entityJPanel.getTableModel().getValueAt(selectedRow, USERNAME).toString();
                    String email = entityJPanel.getTableModel().getValueAt(selectedRow, EMAIL).toString();
                    removeCustomer(username, email);
                    entityJPanel.getTableModel().removeRow(selectedRow);
                    JOptionPane.showMessageDialog(viewManager, "Customer has been deleted!",
                            "Remove Customer", JOptionPane.INFORMATION_MESSAGE);
                } catch (RemoveUserException e1) {
                    System.err.println(e1.getMessage());
                    JOptionPane.showMessageDialog(viewManager, "Cannot remove customer",
                            "Remove Customer", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class UseDatabaseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (viewManager.getUseDatabaseItem().isSelected()) {
                LocatorDAO.getInstance().switchDAOType(DAOSelector.H2);
                DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
                try {
                    EntityTableModel tableModel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL).getTableModel();
                    tableModel.removeAllRows();
                    tableModel.setContent(customerDAO.getAll());
                } catch (SearchException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(viewManager, "Cannot use the database",
                            "Use Database", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                LocatorDAO.getInstance().switchDAOType(DAOSelector.FILE);
                DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
                try {
                    EntityTableModel tableModel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL).getTableModel();
                    tableModel.removeAllRows();
                    tableModel.setContent(customerDAO.getAll());
                } catch (SearchException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(viewManager, "Cannot use the database",
                            "Use Database", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class DbSQLOptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (viewManager.getDbSQLOption().isSelected()) {
                LocatorDAO.getInstance().switchDAOType(DAOSelector.H2);
                DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
                EntityTableModel<Customer> tableModel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL).getTableModel();
                try {
                    tableModel.removeAllRows();
                    tableModel.setContent(customerDAO.getAll());
                    System.out.println("Se seleccionó SQL como tipo de almacenamiento.");
                } catch (SearchException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(viewManager, "Cannot use DB SQL",
                            "DB SQL", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public class DiskOptionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (viewManager.getDiskOption().isSelected()) {
                LocatorDAO.getInstance().switchDAOType(DAOSelector.FILE);
                DAO<Customer> customerDAO = LocatorDAO.getInstance().getCustomerDAO();
                EntityTableModel<Customer> tableModel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL).getTableModel();
                try {
                    tableModel.removeAllRows();
                    tableModel.setContent(customerDAO.getAll());
                    System.out.println("Se seleccionó File como tipo de almacenamiento.");
                } catch (SearchException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(viewManager, "Cannot use Disk",
                            "Disk", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
