package com.http.post.repository;

import com.http.post.model.Body;
import com.http.post.model.Method;
import com.http.post.model.Request;
import com.http.post.utils.bussiness.exceptions.GetException;
import com.http.post.utils.bussiness.exceptions.SearchException;
import orm.BaseORM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestDAOPostgresV2 extends BaseORM<Request> {
    public RequestDAOPostgresV2() {
        super(Request.class);
    }

    public List<Request> getAll() throws SearchException {
        StringBuilder sql = new StringBuilder("SELECT r.id AS request_id, r.url, r.method, b.id AS body_id, b.content, b.contentType ");
        sql.append("FROM request r ")
                .append("LEFT JOIN body b ON r.id = b.requestid ");
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                List<Request> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(mapResultSetToEntity(rs));
                }
                return result;
            }
        } catch (Exception e) {
            throw new SearchException(e.getMessage());
        }
    }

    public Optional<Request> get(Long id) throws GetException {
        StringBuilder sql = new StringBuilder("SELECT r.id AS request_id, r.url, r.method, b.id AS body_id, b.content, b.contentType ");
        sql.append("FROM request r ")
                .append("LEFT JOIN body b ON r.id = b.requestid ")
                .append("WHERE r.id = ?");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Request request = new Request(
                            rs.getString("url"),
                            Method.valueOf(rs.getString("method").toUpperCase())
                    );
                    request.setId(rs.getLong("request_id"));

                    // Map Body if available
                    if (rs.getString("content") != null) {
                        Body body = new Body(rs.getString("contentType"), rs.getString("content"));
                        body.setId(rs.getLong("body_id"));
                        request.setBody(body);
                    }
                    return Optional.of(request);
                }
            }
        } catch (SQLException e) {
            throw new GetException(e.getMessage());
        }
        return Optional.empty();
    }
}
