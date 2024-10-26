package com.http.post.repository;

import com.http.post.model.Request;
import com.http.post.utils.DBManager;
import com.http.post.utils.bussiness.exceptions.*;
import com.http.post.utils.exceptions.DBOperationManager;
import com.http.post.utils.exceptions.SQLActionException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.http.post.model.RequestParser.parse;

public class RequestDAOH2 implements DAO<Request> {

    @Override
    public void create(Request request) throws CreateException {
        String url = request.getUrl();
        String method = request.getMethod().toString();
        String json = request.toJson();
        String isFavorite = request.getFavorite() ? "TRUE" : "FALSE";
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                Statement s = c.createStatement();
                String sql = "INSERT INTO request (url, method, json_data, is_favorite) VALUES ('" + url + "', '" + method + "', '" + json + "', " + isFavorite + ")";
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
                    Request requestFromDB = parse(rs.getString("json_data"));
                    requestFromDB.setId(rs.getLong("id"));
                    return Optional.of(requestFromDB);
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
        Long id = request.getId();
        String url = request.getUrl();
        String method = request.getMethod().toString();
        String isFavorite = request.getFavorite() ? "1" : "0";
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "UPDATE request set url = '" + url + "', method = '" + method + "', json_data = '" + request.toJson() + "', is_favorite = " + isFavorite + " WHERE  id = " + id;
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
    public void delete(Long id) throws DeletionException {
        Connection c = DBManager.connect();
        try {
            DBOperationManager.getInstance().trySqlAction(c, () -> {
                String sql = "DELETE FROM request WHERE id = " + id ;
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
                        Request request = parse(json);
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

    @Override
    public void upsert(Request object) throws UpsertException {
        Long id = object.getId();
        try {
        if (id == null) {
            create(object);
        } else {
            update(object);
        }
        } catch (UpdateException | CreateException e) {
            throw new UpsertException(e.getMessage());
        }
    }
}
