package com.tungstun.core.domain.bar;

import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BarRepository {
    Bar save(Bar bar);
    Bar update(Bar bar);
    void delete(Long id);

    Optional<Bar> findById(Long id);
}
