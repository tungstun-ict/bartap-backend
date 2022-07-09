package com.tungstun.core.port.persistence.bar;

import com.tungstun.core.domain.bar.Bar;
import com.tungstun.core.domain.bar.BarRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DefaultBarRepository implements BarRepository {
    private final SpringBarRepository repository;

    public DefaultBarRepository(SpringBarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bar save(Bar bar) {
        return repository.save(bar);
    }

    @Override
    public Bar update(Bar bar) {
        return repository.save(bar);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Bar> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Bar> findAllById(List<Long> barIds) {
        return repository.findAllById(barIds);
    }
}
