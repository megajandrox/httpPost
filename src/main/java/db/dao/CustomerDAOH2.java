package db.dao;

import commons.db.utils.DBManager;
import commons.db.utils.bussiness.exceptions.*;
import commons.db.utils.exceptions.DBOperationManager;
import commons.db.utils.exceptions.SQLActionException;
import db.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOH2 implements DAO<Customer> {

    @Override
    public void create(Customer customer) throws CreateException {
        String username = customer.getUsername();
        String email = customer.getEmail();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                Statement s = c.createStatement();
                String sql = "INSERT INTO customer (username, email) VALUES ('" + username + "', '" + email + "' )";
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error inserting the customer";
            System.err.println(msgError);
            throw new CreateException(msgError);
        }
    }

    @Override
    public Optional<Customer> read(Customer customer) throws GetException {
        Connection c = DBManager.connect();
        try {
            Optional<Customer> optResult = DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "SELECT * FROM customer WHERE username = '" + customer.getUsername() + "'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    return Optional.of(new Customer(username, email));
                }
                return Optional.empty();
            }, c::rollback);
            return optResult;
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the customer";
            System.err.println(msgError);
            throw new GetException(msgError);
        }
    }

    @Override
    public void update(Customer customer) throws UpdateException {
        String username = customer.getUsername();
        String email = customer.getEmail();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "UPDATE customer set email = '" + email + "' WHERE username = '" + username + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the customer";
            System.err.println(msgError);
            throw new UpdateException(msgError);
        }
    }

    @Override
    public void delete(Customer customer) throws DeletionException {
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "DELETE FROM customer WHERE username = '" + customer.getUsername() + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error deleting the customer";
            System.err.println(msgError);
            throw new DeletionException(msgError);
        }
    }

    @Override
    public List<Customer> getAll() throws SearchException {
            List<Customer> result = new ArrayList<>();
            Connection c = DBManager.connect();
            try {
                DBOperationManager.getInstance().trySqlAction(c, () -> {
                    String sql = "SELECT * FROM customer";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        result.add(new Customer(username, email));
                    }
                    return Optional.empty();
                }, c::rollback);
            } catch (SQLActionException e) {
                String msgError = "There was an error getting the customer";
                System.err.println(msgError);
                throw new SearchException(msgError);
            }
            return result;
        }
}
