package uk.ac.westminster.products_api.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.westminster.products_api.model.DigitalProduct;
import uk.ac.westminster.products_api.model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * No Mockito here: the repository has no collaborators, so it is created
 * with new. Its constructor adds seed products, so the tests never assume
 * a new product gets id 1.
 */
class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository(); // fresh for every test
    }

    @Test
    void shouldAssignIdWhenSavingNewProduct() {
        Product notes = new DigitalProduct(null, "Course notes", 4.99, "/dl/2", 1.1);

        Product saved = repository.save(notes);

        assertNotNull(saved.getId());
        assertSame(notes, repository.findById(saved.getId()).get());
    }

    @Test
    void shouldKeepIdWhenSavingExistingProduct() {
        Product notes = repository.save(new DigitalProduct(null, "Course notes", 4.99, "/dl/2", 1.1));
        Long id = notes.getId();
        int sizeBefore = repository.findAll().size();

        notes.setName("Updated notes");
        repository.save(notes);

        assertEquals(id, notes.getId());
        assertEquals(sizeBefore, repository.findAll().size());
        assertEquals("Updated notes", repository.findById(id).get().getName());
    }

    @Test
    void shouldReturnEmptyWhenIdDoesNotExist() {
        assertTrue(repository.findById(999L).isEmpty());
    }

    @Test
    void shouldDeleteProduct() {
        Product saved = repository.save(new DigitalProduct(null, "Course notes", 4.99, "/dl/2", 1.1));

        repository.delete(saved.getId());

        assertTrue(repository.findById(saved.getId()).isEmpty());
    }

}
