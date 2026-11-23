package uk.ac.westminster.products_api.service;

import org.springframework.stereotype.Service;
import uk.ac.westminster.products_api.exception.ProductNotFoundException;
import uk.ac.westminster.products_api.model.Product;
import uk.ac.westminster.products_api.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business layer for products.
 *
 * There is no HTTP code in this class (no URLs, no ResponseEntity, no
 * status codes) and no storage code (no list, no map, no id counter).
 * It holds the rules and talks to storage only through the
 * ProductRepository interface, which Spring injects through the
 * constructor.
 */
@Service
public class ProductService {

    // The interface, never the implementation. The only field, and final:
    // Spring creates ONE ProductService shared by every request, so no
    // per-request data may be kept here.
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        getProductById(id);       // throws ProductNotFoundException (404) if it is not there
        repository.delete(id);
    }

    /**
     * One summary per product. No instanceof, no casts, no type names:
     * each object runs its own getSummary() (polymorphism), so a new
     * subclass appears here without changing a line.
     */
    public List<String> getAllSummaries() {
        List<String> summaries = new ArrayList<>();
        for (Product p : repository.findAll()) {
            summaries.add(p.getSummary());
        }
        return summaries;
    }

    /**
     * Products whose name contains the given text, ignoring upper/lower case.
     * Read it as: go through the list, keep the ones that match, collect what survived.
     */
    public List<Product> findByName(String name) {
        String wanted = name.toLowerCase();
        return repository.findAll().stream()
                .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(wanted))
                .collect(Collectors.toList());
    }

}
