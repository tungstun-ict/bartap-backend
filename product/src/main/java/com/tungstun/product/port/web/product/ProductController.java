package com.tungstun.product.port.web.product;

import com.tungstun.common.web.IdResponse;
import com.tungstun.product.application.product.ProductCommandHandler;
import com.tungstun.product.application.product.ProductQueryHandler;
import com.tungstun.product.application.product.command.CreateProduct;
import com.tungstun.product.application.product.command.DeleteProduct;
import com.tungstun.product.application.product.command.UpdateProduct;
import com.tungstun.product.application.product.query.GetProduct;
import com.tungstun.product.application.product.query.ListProductsOfBar;
import com.tungstun.product.application.product.query.ListProductsOfCategory;
import com.tungstun.product.domain.product.Product;
import com.tungstun.product.port.web.product.request.CreateProductRequest;
import com.tungstun.product.port.web.product.request.ListProductsOfCategoryRequest;
import com.tungstun.product.port.web.product.request.UpdateProductRequest;
import com.tungstun.product.port.web.product.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/bars/{barId}/products")
public class ProductController {
    private final ProductCommandHandler commandHandler;
    private final ProductQueryHandler queryHandler;

    public ProductController(ProductCommandHandler commandHandler, ProductQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new product",
            description = "A new product is created with information provided in the request body for the bar with the given id",
            tags = "Product"
    )
    public IdResponse createProduct(
            @PathVariable("barId") Long barId,
            @RequestBody CreateProductRequest request) {
        Long id = commandHandler.handle(new CreateProduct(
                barId,
                request.name(),
                request.brand(),
                request.size(),
                request.price(),
                request.type(),
                request.categoryId()
        ));
        return new IdResponse(id);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update product",
            description = "The product with the given id is updated with the information provided in the request body",
            tags = "Product"
    )
    public IdResponse updateProduct(
            @PathVariable("barId") Long barId,
            @PathVariable("productId") Long productId,
            @RequestBody UpdateProductRequest request) {
        Long id = commandHandler.handle(new UpdateProduct(
                productId,
                barId,
                request.name(),
                request.brand(),
                request.size(),
                request.price(),
                request.type(),
                request.categoryId(),
                request.isFavorite()
        ));
        return new IdResponse(id);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete product",
            description = "The product with given id is deleted",
            tags = "Product"
    )
    public void deleteProduct(
            @PathVariable("barId") Long barId,
            @PathVariable("productId") Long productId) {
        commandHandler.handle(new DeleteProduct(productId, barId));
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get product",
            description = "The product with given id is queried",
            tags = "Product"
    )
    public ProductResponse getProduct(
            @PathVariable("barId") Long barId,
            @PathVariable("productId") Long productId) {
        Product product = queryHandler.handle(new GetProduct(productId, barId));
        return ProductResponse.from(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get products of bar",
            description = "The products of bar with given id are queried",
            tags = "Product"
    )
    public List<ProductResponse> getProductOfBar(
            @PathVariable("barId") Long barId,
            @PathParam("categoryId") Long categoryId,
            @PathParam("isFavorite") Boolean isFavorite,
            @PathParam("productType") String productType,
            @PathParam("search") String searchText) {
        return queryHandler.handle(new ListProductsOfBar(barId, categoryId, isFavorite, productType, searchText))
                .parallelStream()
                .map(ProductResponse::from)
                .toList();
    }
}
