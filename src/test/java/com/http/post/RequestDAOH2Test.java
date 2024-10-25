package com.http.post;

import com.http.post.model.Method;
import com.http.post.model.Request;
import com.http.post.repository.DAO;
import com.http.post.repository.RequestDAOH2;
import com.http.post.repository.TableManager;
import com.http.post.utils.bussiness.exceptions.CreateException;
import com.http.post.utils.bussiness.exceptions.GetException;
import com.http.post.utils.bussiness.exceptions.UpdateException;
import org.junit.Assert;

import java.util.Optional;

public class RequestDAOH2Test {

    private DAO<Request> requestDAO = new RequestDAOH2();

    @org.junit.Before
    public void setUp() throws Exception {
        TableManager.createRequestTable();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        //TableManager.dropRequestTable();
    }

    @org.junit.Test
    public void create() throws CreateException, GetException {
        requestDAO.create(new Request("http://localhost:8080/test", Method.GET));
        Optional<Request> read = requestDAO.read(new Request("http://localhost:8080/test", Method.GET));
        read.ifPresent(r -> Assert.assertEquals("http://localhost:8080/test", r.getUrl()));
    }

    @org.junit.Test
    public void update() throws CreateException, GetException, UpdateException {
        requestDAO.create(new Request("http://localhost:8080/test", Method.GET));
        Request request = requestDAO.read(new Request("http://localhost:8080/test", Method.GET)).get();
        request.setFavorite(false);
        requestDAO.update(request);
        Optional<Request> read = requestDAO.read(request);
        read.ifPresent(r -> Assert.assertEquals("http://localhost:8080/test", r.getUrl()));
        read.ifPresent(r -> Assert.assertEquals(r.getFavorite(), request.getFavorite()));
        read.ifPresent(r -> Assert.assertEquals(r.getMethod(), request.getMethod()));
    }
}