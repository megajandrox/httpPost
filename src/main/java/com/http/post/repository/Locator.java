package com.http.post.repository;

import com.http.post.model.Request;

import java.util.Objects;

public class Locator {

    private static final Locator INSTANCE = new Locator();

    public static Locator getInstance() {
        return INSTANCE;
    }

    private DAO<Request> requestDAO = new RequestDAOPostgres();

    public DAO<Request> getRequestDAO() {
        return requestDAO;
    }

    private Locator() {
        switchDAOType(DAOType.H2);
    }

    public void switchDAOType(DAOType selector) {
        if (Objects.requireNonNull(selector) == DAOType.FILE) {
            requestDAO = new RequestDAOJson();
        } else {
            requestDAO = new RequestDAOPostgres();
        }
    }
}
