package uk.ac.westminster.products_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.westminster.products_api.exception.ProductNotFoundException;
import uk.ac.westminster.products_api.model.DigitalProduct;
import uk.ac.westminster.products_api.model.Product;
import uk.ac.westminster.products_api.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests only the web layer: ProductController plus GlobalExceptionHandler.
 * The service is a Mockito mock, so no real business logic or storage runs.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private final Product ebook =
            new DigitalProduct(1L, "E-book", 9.99, "/dl/1", 4.2);

    @Test
    void shouldReturn200AndProductWhenFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(ebook);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("E-book"))
                .andExpect(jsonPath("$.type").value("digital"));
    }

    @Test
    void shouldReturn404WithErrorBodyWhenNotFound() throws Exception {
        when(productService.getProductById(99L)).thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Product not found with id: 99"));
    }

    @Test
    void shouldReturn201WhenProductCreated() throws Exception {
        when(productService.addProduct(any(Product.class))).thenReturn(ebook);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type": "digital", "name": "E-book", "price": 9.99,
                                 "downloadUrl": "/dl/1", "fileSizeMB": 4.2}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn204WhenProductDeleted() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingMissingProduct() throws Exception {
        doThrow(new ProductNotFoundException(99L)).when(productService).deleteProduct(99L);

        mockMvc.perform(delete("/products/99"))
                .andExpect(status().isNotFound());
    }

}
