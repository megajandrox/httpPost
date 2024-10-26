package com.http.post.repository;

import com.http.post.utils.bussiness.exceptions.*;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void create(T object) throws CreateException;
    Optional<T> get(T object) throws GetException;
    void update(T object) throws UpdateException;
    void delete(Long id) throws DeletionException;
    List<T> getAll() throws SearchException;
    void upsert(T object) throws UpsertException;
}
