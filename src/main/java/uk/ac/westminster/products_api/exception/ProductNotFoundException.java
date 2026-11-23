package uk.ac.westminster.products_api.exception;

/**
 * Unchecked: it extends RuntimeException, so nothing is forced to declare
 * throws or to catch it.
 *
 * Thrown by ProductService when no product has the requested id. It says
 * something true about the domain and nothing about HTTP;
 * GlobalExceptionHandler decides that the client sees 404.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }

}
