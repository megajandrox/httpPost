package com.http.post.repository;

import com.http.post.model.Request;
import com.http.post.utils.bussiness.exceptions.*;
import file.JsonManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RequestDAOJson extends JsonManager<Request> implements DAO<Request> {

    public RequestDAOJson() {
        super("resquest");
    }

    @Override
    public void create(Request request) throws CreateException {
        request.setId(System.currentTimeMillis());
        write(Collections.singletonList(request), Request.class);
    }

    @Override
    public Optional<Request> get(Long id) throws GetException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Request request) throws UpdateException {
        updateObject(request, Request.class, request1 -> request1.getId().equals(request.getId()));
    }

    @Override
    public int delete(Long id) throws DeletionException {
        try {
            removeObject(readAll(Request.class), request -> request.getId().equals(id));
        } catch (SearchException e) {
            throw new DeletionException("There was an error deleting the object");
        }
        return 1;
    }

    @Override
    public List<Request> getAll() throws SearchException {
        return readAll(Request.class);
    }
}
