package com.http.post.service;

import com.http.post.model.Request;
import com.http.post.repository.DAO;
import com.http.post.utils.bussiness.exceptions.*;

import java.util.List;
import java.util.Optional;

public class RequestService {

    private DAO<Request> requestDAO;

    public RequestService(DAO<Request> requestDAO) {
        this.requestDAO = requestDAO;
    }

    public void createRequest(Request request) throws ServiceException {
        try {
            requestDAO.create(request);
        } catch (CreateException e) {
            throw new ServiceException("Failed to create request", e);
        }
    }

    public Optional<Request> getRequest(Request request) throws ServiceException {
        try {
            return requestDAO.get(request.getId());
        } catch (GetException e) {
            throw new ServiceException("Failed to retrieve request", e);
        }
    }

    public void updateRequest(Request request) throws ServiceException {
        try {
            requestDAO.update(request);
        } catch (UpdateException e) {
            throw new ServiceException("Failed to update request", e);
        }
    }

    public void deleteRequest(Long id) throws ServiceException {
        try {
            requestDAO.delete(id);
        } catch (DeletionException e) {
            throw new ServiceException("Failed to delete request", e);
        }
    }

    public List<Request> getAllRequests() throws ServiceException {
        try {
            return requestDAO.getAll();
        } catch (SearchException e) {
            throw new ServiceException("Failed to retrieve all requests", e);
        }
    }
}
