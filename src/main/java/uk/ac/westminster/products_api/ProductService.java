package uk.ac.westminster.products_api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Seeding both types: one List<Product>, two kinds of product.
    // No cast is needed - a DigitalProduct IS-A Product, and so is a PhysicalProduct.
    public ProductService() {
        products.add(new DigitalProduct(nextId++, "Java eBook", 9.99,
                "https://example.com/downloads/java-ebook.pdf", 12.5));
        products.add(new PhysicalProduct(nextId++, "Mechanical Keyboard", 79.99,
                0.9, "44 x 14 x 4 cm"));
    }

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

    public void deleteProduct(Long id) {
        products.removeIf(p -> p.getId().equals(id)); // safe, unlike remove() inside a for loop
    }

    /**
     * Products whose name contains the given text, ignoring upper/lower case.
     * Read it as: go through the list, keep the ones that match, collect what survived.
     */
    public List<Product> findByName(String name) {
        String wanted = name.toLowerCase();
        return products.stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(wanted))
                .collect(Collectors.toList());
    }

}
