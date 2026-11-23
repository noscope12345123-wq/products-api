package uk.ac.westminster.products_api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.westminster.products_api.exception.ProductNotFoundException;
import uk.ac.westminster.products_api.model.DigitalProduct;
import uk.ac.westminster.products_api.model.PhysicalProduct;
import uk.ac.westminster.products_api.model.Product;
import uk.ac.westminster.products_api.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests ProductService on its own: no Spring, no server, no real storage.
 * The repository is a Mockito mock, so a failing test here points at a
 * business rule in the service, never at storage.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;   // "make me a fake of this interface"

    @InjectMocks
    private ProductService service;         // "make the REAL class and give it the fakes"

    private final Product ebook =
            new DigitalProduct(1L, "E-book", 9.99, "/dl/1", 4.2);
    private final Product monitor =
            new PhysicalProduct(2L, "Monitor", 249.99, 5.4, "60x40");

    @Test
    void shouldReturnAllProducts() {
        when(repository.findAll()).thenReturn(List.of(ebook, monitor));

        List<Product> result = service.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("E-book", result.get(0).getName());
    }

    @Test
    void shouldReturnProductById() {
        when(repository.findById(1L)).thenReturn(Optional.of(ebook));

        Product result = service.getProductById(1L);

        assertEquals("E-book", result.getName());
        assertEquals(9.99, result.getPrice(), 0.001);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.getProductById(99L));
    }

    @Test
    void shouldSaveNewProduct() {
        Product notes = new DigitalProduct(null, "Course notes", 4.99, "/dl/2", 1.1);
        when(repository.save(notes)).thenReturn(notes);

        Product result = service.addProduct(notes);

        verify(repository).save(notes);
        assertEquals("Course notes", result.getName());
    }

    @Test
    void shouldDeleteProductWhenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(ebook));

        service.deleteProduct(1L);

        verify(repository).delete(1L);
    }

    @Test
    void shouldNotDeleteWhenProductMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> service.deleteProduct(99L));

        verify(repository, never()).delete(any());
    }

    @Test
    void shouldReturnOneSummaryPerProductFromEachSubclass() {
        when(repository.findAll()).thenReturn(List.of(ebook, monitor));

        List<String> summaries = service.getAllSummaries();

        assertEquals(List.of("Digital: E-book | Download: /dl/1",
                             "Physical: Monitor | 5.4kg"), summaries);
    }

    @Test
    void shouldFindByNameIgnoringCase() {
        when(repository.findAll()).thenReturn(List.of(ebook, monitor));

        List<Product> result = service.findByName("MONI");

        assertEquals(1, result.size());
        assertEquals("Monitor", result.get(0).getName());
    }

}
