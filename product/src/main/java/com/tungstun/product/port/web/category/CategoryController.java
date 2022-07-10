package com.tungstun.product.port.web.category;

import com.tungstun.product.application.category.CategoryCommandHandler;
import com.tungstun.product.application.category.CategoryQueryHandler;
import com.tungstun.product.application.category.command.CreateCategory;
import com.tungstun.product.application.category.command.DeleteCategory;
import com.tungstun.product.application.category.command.UpdateCategory;
import com.tungstun.product.application.category.query.GetCategory;
import com.tungstun.product.application.category.query.ListCategoriesOfBar;
import com.tungstun.product.domain.category.Category;
import com.tungstun.product.port.web.category.request.CreateCategoryRequest;
import com.tungstun.product.port.web.category.request.UpdateCategoryRequest;
import com.tungstun.product.port.web.category.response.CategoryIdResponse;
import com.tungstun.product.port.web.category.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryCommandHandler commandHandler;
    private final CategoryQueryHandler queryHandler;

    public CategoryController(CategoryCommandHandler commandHandler, CategoryQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new category",
            description = "A new category is created with given name for the bar with provided id",
            tags = "Category"
    )
    public CategoryIdResponse createCategory(@RequestBody CreateCategoryRequest request) {
        Long id = commandHandler.handle(new CreateCategory(request.name(), request.barId()));
        return new CategoryIdResponse(id);
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update category",
            description = "The category with the given id is updated with the new name provided in the request body",
            tags = "Category"
    )
    public CategoryIdResponse updateCategory(@PathVariable("categoryId") Long id,
                                             @RequestBody UpdateCategoryRequest request) {
        commandHandler.handle(new UpdateCategory(id, request.name()));
        return new CategoryIdResponse(id);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete category",
            description = "The category with given id is deleted",
            tags = "Category"
    )
    public void deleteCategory(@PathVariable("categoryId") Long id) {
        commandHandler.handle(new DeleteCategory(id));
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get category",
            description = "The category with given id is queried",
            tags = "Category"
    )
    public CategoryResponse getCategory(@PathVariable("categoryId") Long id) {
        Category category = queryHandler.handle(new GetCategory(id));
        return CategoryResponse.from(category);
    }

    @GetMapping("/bars/{barId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get categories of bar",
            description = "The categories of bar with given id are queried",
            tags = "Category"
    )
    public List<CategoryResponse> getCategoryOfBar(@PathVariable("barId") Long id) {
        return queryHandler.handle(new ListCategoriesOfBar(id))
                .parallelStream()
                .map(CategoryResponse::from)
                .toList();
    }
}
