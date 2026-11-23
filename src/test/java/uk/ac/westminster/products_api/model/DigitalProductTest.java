package uk.ac.westminster.products_api.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DigitalProductTest {

    private DigitalProduct ebook;

    @BeforeEach
    void setUp() {
        ebook = new DigitalProduct(1L, "E-book", 9.99, "/dl/1", 4.2);
    }

    // ---- price rule (Lecture 2) ----

    @Test
    void shouldUpdatePriceWhenValid() {
        ebook.setPrice(7.49);

        assertEquals(7.49, ebook.getPrice(), 0.001);
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> ebook.setPrice(-0.01));
    }

    @Test
    void shouldAcceptZeroPrice() {
        ebook.setPrice(0);

        assertEquals(0, ebook.getPrice(), 0.001);
    }

    @Test
    void shouldRejectEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> ebook.setName("  "));
    }

    // ---- discount rule (Lecture 5): 0 to 0.20 accepted, anything else rejected ----

    @Test
    void shouldApplyDiscountWhenRateIsTenPercent() {
        ebook.applyDiscount(0.10);

        assertEquals(8.991, ebook.getDiscountedPrice(), 0.001);
    }

    @Test
    void shouldKeepFullPriceWhenRateIsZero() {
        ebook.applyDiscount(0);

        assertEquals(9.99, ebook.getDiscountedPrice(), 0.001);
    }

    @Test
    void shouldAcceptDiscountWhenRateIsExactlyTwentyPercent() {
        ebook.applyDiscount(0.20);

        assertEquals(7.992, ebook.getDiscountedPrice(), 0.001);
    }

    @Test
    void shouldRejectDiscountWhenRateIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> ebook.applyDiscount(-0.01));
    }

    @Test
    void shouldRejectDiscountWhenRateIsAboveTwentyPercent() {
        assertThrows(IllegalArgumentException.class, () -> ebook.applyDiscount(0.21));
    }

    @Test
    void shouldKeepPriceWhenDiscountIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> ebook.applyDiscount(0.21));

        assertEquals(9.99, ebook.getDiscountedPrice(), 0.001);
    }

    // ---- polymorphism (Lecture 5) ----

    @Test
    void shouldDescribeItselfAsDigitalWhenSeenAsProduct() {
        Product p = ebook; // declared type Product, object DigitalProduct

        assertEquals("Digital: E-book | Download: /dl/1", p.getSummary());
    }

}
