package com.http.post.service;

import com.http.post.repository.RequestDAOJson;
import com.http.post.repository.RequestDAOPostgresV2;

import java.util.Objects;

public class ServiceLocator {

    private static final ServiceLocator INSTANCE = new ServiceLocator();

    public static ServiceLocator getInstance() {
        return INSTANCE;
    }

    private RequestService requestService = new RequestService(new RequestDAOPostgresV2());


    private ServiceLocator() {
        switchDBType(DBType.RELATIONAL);
    }

    public void switchDBType(DBType selector) {
        if (Objects.requireNonNull(selector) == DBType.JSON) {
            requestService = new RequestService(new RequestDAOJson());
        } else {
            requestService = new RequestService(new RequestDAOPostgresV2());
        }
    }

    public RequestService getRequestService() {
        return requestService;
    }
}
