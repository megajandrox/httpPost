package com.http.post.repository;

import com.http.post.model.Entity;
import com.http.post.utils.bussiness.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface DAO<T extends Entity> {
    void create(T entity) throws CreateException;
    Optional<T> get(Long id) throws GetException;
    void update(T entity) throws UpdateException;
    int delete(Long id) throws DeletionException;
    List<T> getAll() throws SearchException;
}
