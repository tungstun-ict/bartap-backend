package com.tungstun.product.application.category;

import com.tungstun.product.application.category.command.CreateCategory;
import com.tungstun.product.application.category.command.DeleteCategory;
import com.tungstun.product.application.category.command.UpdateCategory;
import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Service
@Validated
@Transactional
public class CategoryCommandHandler {
    private final CategoryRepository repository;

    public CategoryCommandHandler(CategoryRepository repository) {
        this.repository = repository;
    }

    public Long handle(@Valid CreateCategory command) {
        return repository.save(new Category(command.name(), command.barId())).getId();
    }

    public Long handle(@Valid UpdateCategory command) {
        Category category = repository.findByIdAndBarId(command.id(), command.barId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No category found with id %s", command.id())));
        category.setName(command.name());
        return repository.update(category).getId();
    }

    public void handle(@Valid DeleteCategory command) {
        repository.delete(command.id());
    }
}
