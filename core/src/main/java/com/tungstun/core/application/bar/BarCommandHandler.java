package com.tungstun.core.application.bar;

import com.tungstun.core.application.bar.command.CreateBar;
import com.tungstun.core.application.bar.command.DeleteBar;
import com.tungstun.core.application.bar.command.UpdateBar;
import com.tungstun.core.domain.bar.Bar;
import com.tungstun.core.domain.bar.BarRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
@Validated
public class BarCommandHandler {
    private final BarRepository repository;

    public BarCommandHandler(BarRepository repository) {
        this.repository = repository;
    }

    public Long handle(@Valid CreateBar command) {
        return repository.save(new Bar(command.name(), command.address(), command.mail(), command.phoneNumber())).getId();
    }

    public void handle(@Valid UpdateBar command) {
        Bar bar = repository.findById(command.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No bar found with id %s", command.id())));
        bar.setName(command.name());
        bar.setAddress(command.address());
        bar.setMail(command.mail());
        bar.setPhoneNumber(command.phoneNumber());
        repository.update(bar);
    }

    public void handle(@Valid DeleteBar command) {
        repository.delete(command.id());
    }
}
