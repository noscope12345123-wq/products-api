package uk.ac.westminster.products_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.westminster.products_api.exception.ErrorResponse;
import uk.ac.westminster.products_api.model.Product;
import uk.ac.westminster.products_api.service.ProductService;

import java.util.List;

/**
 * HTTP layer for products. Holds no data and no business rules:
 * every method is one line that calls one ProductService method.
 * Errors are thrown by the service and turned into status codes by
 * GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Create, read, search and delete products in the catalogue")
public class ProductController {

    private final ProductService productService;

    // Constructor injection: Spring creates the ProductService and passes it in.
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products",
               description = "Returns every product in the catalogue - digital, physical and bundle. "
                           + "Responds 200 OK.")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get one summary per product",
               description = "Returns a one-line description of every product. Each product type "
                           + "writes its own summary (polymorphism). Responds 200 OK.")
    @GetMapping("/summaries")
    public List<String> getSummaries() {
        return productService.getAllSummaries();
    }

    @Operation(summary = "Get a product by ID",
               description = "Returns one product. Responds 404 Not Found if no product has this id.")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "No product with this id",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/{id}")
    public Product getProductById(
            @Parameter(description = "Product id", example = "1") @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Add a new product",
               description = "Creates a product from the JSON body and gives it a new id. "
                           + "The body must include \"type\": \"digital\", \"physical\" or \"bundle\". "
                           + "Responds 201 Created with the saved product, or 400 Bad Request "
                           + "if the type is missing, the price is negative or the name is empty.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @Operation(summary = "Delete a product by ID",
               description = "Removes the product. Responds 204 No Content on success, "
                           + "or 404 Not Found if no product has this id.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @Parameter(description = "Product id", example = "1") @PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @Operation(summary = "Search products by name",
               description = "Returns products whose name contains the given text, ignoring case. "
                           + "Responds 200 OK with an empty list if nothing matches.")
    @GetMapping("/search") // e.g. GET /products/search?name=lap
    public List<Product> findByName(
            @Parameter(description = "Text to look for in the name", example = "java") @RequestParam String name) {
        return productService.findByName(name);
    }

}
