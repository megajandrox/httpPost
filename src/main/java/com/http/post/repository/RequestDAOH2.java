package com.http.post.repository;

import com.http.post.model.Method;
import com.http.post.model.Request;
import com.http.post.model.RequestParser;
import commons.db.utils.DBManager;
import commons.db.utils.bussiness.exceptions.*;
import commons.db.utils.exceptions.DBOperationManager;
import commons.db.utils.exceptions.SQLActionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestDAOH2 implements DAO<Request> {

    @Override
    public void create(Request request) throws CreateException {
        String url = request.getUrl();
        String method = request.getMethod().toString();
        String json = request.toJson();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                Statement s = c.createStatement();
                String sql = "INSERT INTO request (url, method, json_data) VALUES ('" + url + "', '" + method + "', '" + json + "')";
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error inserting the request";
            System.err.println(msgError);
            throw new CreateException(msgError);
        }
    }

    @Override
    public Optional<Request> read(Request request) throws GetException {
        Connection c = DBManager.connect();
        try {
            Optional<Request> optResult = DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "SELECT * FROM request WHERE url = '" + request.getUrl() + "' AND method = '" + request.getMethod().toString() + "'";
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);
                if (rs.next()) {
                    String url = rs.getString("url");
                    String method = rs.getString("method");
                    return Optional.of(new Request(url, Method.valueOf(method)));
                }
                return Optional.empty();
            }, c::rollback);
            return optResult;
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the request";
            System.err.println(msgError);
            throw new GetException(msgError);
        }
    }

    @Override
    public void update(Request request) throws UpdateException {
        String url = request.getUrl();
        String method = request.getMethod().toString();
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "UPDATE request set url = '" + url + "' WHERE url = '" + url + "' AND method = '" + method + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error updating the request";
            System.err.println(msgError);
            throw new UpdateException(msgError);
        }
    }

    @Override
    public void delete(Request request) throws DeletionException {
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "DELETE FROM request WHERE url = '" + request.getUrl() + "' AND method = '" + request.getMethod().toString() + "'";
                Statement s = c.createStatement();
                s.executeUpdate(sql);
                c.commit();
                return java.util.Optional.empty();
            }, c::rollback);
        } catch (SQLActionException e) {
            String msgError = "There was an error deleting the request";
            System.err.println(msgError);
            throw new DeletionException(msgError);
        }
    }

    @Override
    public List<Request> getAll() throws SearchException {
            List<Request> result = new ArrayList<>();
            Connection c = DBManager.connect();
            try {
                DBOperationManager.getInstance().trySqlAction(c, () -> {
                    String sql = "SELECT * FROM request";
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery(sql);
                    while (rs.next()) {
                        String json = rs.getString("json_data");
                        Request request = RequestParser.parse(json);
                        request.setId(rs.getLong("id"));
                        result.add(request);
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
