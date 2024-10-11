package db.dao;

import commons.db.utils.DBManager;
import commons.db.utils.Paciente;
import commons.db.utils.bussiness.exceptions.*;
import commons.db.utils.exceptions.DBOperationManager;
import commons.db.utils.exceptions.SQLActionException;
import db.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOH2 implements DAO<Product> {

    @Override
    public void create(Product product) throws CreateException {
        String name = product.getName();
        String description = product.getDescription();
        double price = product.getPrice();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                Statement s = c.createStatement();
                String sql = "INSERT INTO product (name, description, price) VALUES ('" + name + "', '" + description + "', '" + price + "' )";
                s.executeUpdate(sql);
                c.commit();
                return Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error inserting the product";
            System.err.println(msgError);
            throw new CreateException(msgError);
        }
    }

    @Override
    public Optional<Product> read(Product product) throws GetException {
        Connection c = DBManager.connect();
        try {
            Optional<Product> optResult = DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "SELECT * FROM product WHERE name = '" + product.getName() + "'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    return Optional.of(new Product(name, description, price));
                }
                return Optional.empty();
            }, c::rollback);
            return optResult;
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the product";
            System.err.println(msgError);
            throw new GetException(msgError);
        }
    }

    @Override
    public void update(Product product) throws UpdateException {
        String name = product.getName();
        String description = product.getDescription();
        double price = product.getPrice();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "UPDATE product set name = '" + name + "', description = '" + description + "', price = '" + price + "' WHERE name = '" + name + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the product";
            System.err.println(msgError);
            throw new UpdateException(msgError);
        }
    }

    @Override
    public void delete(Product product) throws DeletionException {
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "DELETE FROM product WHERE name = '" + product.getName() + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error deleting the product";
            System.err.println(msgError);
            throw new DeletionException(msgError);
        }
    }

    @Override
    public List<Product> getAll() throws SearchException {
        List<Product> result = new ArrayList<>();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "SELECT * FROM product";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                while(rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    result.add(new Product(name, description, price));
                }
                return Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error getting the product";
            System.err.println(msgError);
            throw new SearchException(msgError);
        }
        return result;
    }
}
