package uk.ac.westminster.products_api.repository;

import uk.ac.westminster.products_api.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * A contract. No annotation, no bodies, no fields.
 * It says WHAT storage must do, never HOW.
 *
 * ProductService depends on this interface, never on a concrete class,
 * so the storage could be replaced (for example by a database) without
 * changing a line of business logic.
 */
public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    /** Gives the product an id if it has none, stores it and returns it. */
    Product save(Product product);

    /** Removes the product with that id if it is there. */
    void delete(Long id);

}
