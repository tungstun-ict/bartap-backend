package com.tungstun.product.application.category;

import com.tungstun.product.application.category.query.GetCategory;
import com.tungstun.product.application.category.query.ListCategoriesOfBar;
import com.tungstun.product.domain.category.Category;
import com.tungstun.product.domain.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class CategoryQueryHandler {
    private final CategoryRepository repository;

    public CategoryQueryHandler(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category handle(@Valid GetCategory command) {
        return repository.findByIdAndBarId(command.id(), command.barId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("No category found with id %s", command.id())));
    }

    public List<Category> handle(@Valid ListCategoriesOfBar command) {
        return repository.findAllOfBar(command.barId());
    }
}
