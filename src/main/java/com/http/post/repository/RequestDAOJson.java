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
    public Optional<Request> read(Request request) throws GetException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Request request) throws UpdateException {
        updateObject(request, Request.class, request1 -> request1.getId().equals(request.getId()));
    }

    @Override
    public void delete(Long id) throws DeletionException {
        try {
            removeObject(readAll(Request.class), request -> request.getId().equals(id));
        } catch (SearchException e) {
            throw new DeletionException("There was an error deleting the object");
        }
    }

    @Override
    public List<Request> getAll() throws SearchException {
        return readAll(Request.class);
    }

    @Override
    public void upsert(Request request) throws UpsertException {
        Long id = request.getId();
        try {
            if (id == null) {
                create(request);
            } else {
                update(request);
            }
        } catch (UpdateException | CreateException e) {
            throw new UpsertException(e.getMessage());
        }
    }
}
