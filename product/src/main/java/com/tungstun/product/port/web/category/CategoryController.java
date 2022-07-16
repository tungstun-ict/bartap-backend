package com.tungstun.product.port.web.category;

import com.tungstun.common.web.IdResponse;
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
import com.tungstun.product.port.web.category.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/categories")
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
            description = "A new category is created with given name for the bar with given id",
            tags = "Category"
    )
    public CategoryIdResponse createCategory(
            @PathVariable("barId") Long barId,
            @RequestBody CreateCategoryRequest request) {
        Long id = commandHandler.handle(new CreateCategory(request.name(), barId));
        return new CategoryIdResponse(id);
    }

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update category",
            description = "The category with the given id is updated with the new name provided in the request body",
            tags = "Category"
    )
    public CategoryIdResponse updateCategory(
            @PathVariable("barId") Long barId,
            @PathVariable("categoryId") Long id,
            @RequestBody UpdateCategoryRequest request) {
        commandHandler.handle(new UpdateCategory(id, request.name(), barId));
        return new CategoryIdResponse(id);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete category",
            description = "The category with given id is deleted",
            tags = "Category"
    )
    public void deleteCategory(
            @PathVariable("barId") Long barId,
            @PathVariable("categoryId") Long id) {
        commandHandler.handle(new DeleteCategory(id, barId));
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get category",
            description = "The category with given id is queried",
            tags = "Category"
    )
    public CategoryResponse getCategory(
            @PathVariable("barId") Long barId,
            @PathVariable("categoryId") Long id) {
        Category category = queryHandler.handle(new GetCategory(id, barId));
        return CategoryResponse.from(category);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get categories of bar",
            description = "The categories of bar with given id are queried",
            tags = "Category"
    )
    public List<CategoryResponse> getCategoryOfBar(@PathVariable("barId") Long barId) {
        return queryHandler.handle(new ListCategoriesOfBar(barId))
                .parallelStream()
                .map(CategoryResponse::from)
                .toList();
    }
}
