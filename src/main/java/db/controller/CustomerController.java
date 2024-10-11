package db.controller;

import commons.db.utils.bussiness.exceptions.CreateException;
import commons.db.utils.bussiness.exceptions.DeletionException;
import db.controller.exception.RemoveUserException;
import db.controller.exception.SaveUserException;
import db.dao.DAO;
import db.model.Customer;
import db.view.panel.EntityJPanel;
import db.view.JViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static db.view.table.CustomerTableModel.EMAIL;
import static db.view.table.CustomerTableModel.USERNAME;

public class CustomerController {

    public static final int CUSTOMER_PANEL = 0;
    private final JViewManager viewManager;
    private DAO customerDAO;

    public CustomerController(DAO customerDAO, JViewManager viewManager) {
        this.customerDAO = customerDAO;
        this.viewManager = viewManager;
        EntityJPanel entityJPanel = viewManager.getMainPanel().getEntityJPanels().get(CUSTOMER_PANEL);
        entityJPanel.getAddButton().addActionListener(new CustomerController.NewCustomerListener());
        entityJPanel.getRemoveButton().addActionListener(new CustomerController.RemoveCustomerListener());
    }

    private void saveCustomer(String username, String email) throws SaveUserException {
        try {
            customerDAO.create(new Customer(username, email));
        } catch (CreateException e) {
            throw new SaveUserException(e);
        }
    }

    public void removeCustomer(String username, String email) throws RemoveUserException {
        try {
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
            EntityJPanel entityJPanel = viewManager.getMainPanel().getEntityJPanels().get(0);
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
}
