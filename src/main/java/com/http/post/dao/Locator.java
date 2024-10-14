package com.http.post.dao;

import com.http.post.model.Request;

public class Locator {

    private static final Locator INSTANCE = new Locator();

    public static Locator getInstance() {
        return INSTANCE;
    }

    private DAO<Request> requestDAO = new RequestDAOH2();

    private Locator() {}

    public DAO<Request> getRequestDAO() {
        return requestDAO;
    }
}
