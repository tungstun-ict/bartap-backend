package com.tungstun.common.persistence;

import java.util.Optional;

/**
 * Generic repository fragment for basic CRUD operations.<br>
 * Where <code>T</code> is the Entity persisted and queried behind the repository<br>
 * and <code>ID</code> is the Class used as identifier for <code>T</code><br>
 * */
@SuppressWarnings("java:S119")
public interface CrudRepositoryFragment<T, ID> {
    T save(T entity);
    T update(T entity);
    void delete(ID id);
    Optional<T> findById(ID id);
}
