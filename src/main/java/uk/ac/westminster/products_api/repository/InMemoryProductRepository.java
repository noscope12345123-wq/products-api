package uk.ac.westminster.products_api.repository;

import org.springframework.stereotype.Repository;
import uk.ac.westminster.products_api.model.DigitalProduct;
import uk.ac.westminster.products_api.model.PhysicalProduct;
import uk.ac.westminster.products_api.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Keeps products in memory. This is the only class that knows how products
 * are stored; everything else talks to the ProductRepository interface.
 *
 * Trade-off: simple and fast, but every product is lost when the
 * application restarts, and two running instances would not share data.
 */
@Repository
public class InMemoryProductRepository implements ProductRepository {

    // The store IS the data, so a field is correct here (unlike in the service).
    // A Map keyed by id makes findById and delete one line each;
    // LinkedHashMap keeps products in the order they were added.
    private final Map<Long, Product> store = new LinkedHashMap<>();
    private Long nextId = 1L;

    // Seed data moved here from ProductService. save() assigns the ids.
    public InMemoryProductRepository() {
        store(new DigitalProduct(null, "Java eBook", 9.99,
                "https://example.com/downloads/java-ebook.pdf", 12.5));
        store(new PhysicalProduct(null, "Mechanical Keyboard", 79.99,
                0.9, "44 x 14 x 4 cm"));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(store.values()); // a copy, so callers cannot change the store
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Product save(Product product) {
        return store(product);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    // Private, so the constructor does not call an overridable method.
    private Product store(Product product) {
        if (product.getId() == null) {    // new product: give it an id first,
            product.setId(nextId++);      // or the map key would be null
        }                                 // otherwise it is an update: keep the id
        store.put(product.getId(), product);
        return product;
    }

}
