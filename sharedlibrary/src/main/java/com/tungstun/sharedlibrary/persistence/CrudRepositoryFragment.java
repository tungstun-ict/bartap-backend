package com.tungstun.sharedlibrary.persistence;

import java.util.Optional;

/**
 * Generic repository fragment for basic CRUD operations.<br>
 * Where <code>T</code> is the Entity persisted and queried behind the repository<br>
 * and <code>ID</code> is the Class used as identifier for <code>T</code><br>
 * */
public interface CrudRepositoryFragment<T, ID> {
    T save(T bill);
    T update(T bill);
    void delete(ID id);
    Optional<T> findById(ID id);
}
