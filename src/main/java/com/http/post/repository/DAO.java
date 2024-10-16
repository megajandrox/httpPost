package com.http.post.repository;

import commons.db.utils.bussiness.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void create(T object) throws CreateException;
    Optional<T> read(T object) throws GetException;
    void update(T object) throws UpdateException;
    void delete(Long id) throws DeletionException;
    List<T> getAll() throws SearchException;
}
