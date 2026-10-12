package uk.ac.westminster.products_api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Owns the product catalogue and the rules about it.
 *
 * There is no HTTP code in this class (no URLs, no mappings, no status
 * codes) - it only knows about products. The controller handles HTTP
 * and delegates every piece of logic to this service.
 */
@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    public List<Product> getAllProducts() {
        return products;
    }

    public Product addProduct(Product product) {
        product.setId(nextId++); // use the value, then add one
        products.add(product);
        return product;
    }

    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id)) // .equals, never == on Long
                .findFirst();
    }

}
