package db.dao;

import commons.db.utils.bussiness.exceptions.*;
import commons.db.utils.file.FileManager;
import db.model.Customer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerDAOFile extends FileManager<Customer> implements DAO<Customer> {

    public CustomerDAOFile() {
        super("customer");
    }

    @Override
    public void create(Customer customer) throws CreateException {
        writeFile(Collections.singletonList(customer));
    }

    @Override
    public Optional<Customer> read(Customer customer) throws GetException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Customer customer) throws UpdateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Customer customer) throws DeletionException {
        removeObject(customer.getId());
    }

    @Override
    public List<Customer> getAll() throws SearchException {
        return readAll();
    }
}
