package com.tungstun.core.application.bar;

import com.tungstun.core.application.bar.query.GetBar;
import com.tungstun.core.application.bar.query.GetOwnedBars;
import com.tungstun.core.domain.bar.Bar;
import com.tungstun.core.domain.bar.BarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Service
@Validated
public class BarQueryHandler {
    private final BarRepository repository;

    public BarQueryHandler(BarRepository repository) {
        this.repository = repository;
    }

    public Bar handle(@Valid GetBar query) {
        return repository.findById(query.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bar found with id %s", query.id())));
    }

    public List<Bar> handle(@Valid GetOwnedBars query) {
        return Collections.emptyList(); //todo implement
    }
}
