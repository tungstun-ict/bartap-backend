package com.tungstun.sharedlibrary.persistence;

import java.util.Optional;

/**
 * CRUD repository fragment to generify repositories
 * */
public interface CrudRepositoryFragment<T, ID> {
    T save(T bill);
    T update(T bill);
    void delete(ID id);
    Optional<T> findById(ID id);
}
