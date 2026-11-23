package uk.ac.westminster.products_api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PhysicalProductTest {

    private final PhysicalProduct monitor =
            new PhysicalProduct(2L, "Monitor", 100.00, 5.4, "60x40");

    @Test
    void shouldApplyFullRateWhenBelowShippingCap() {
        monitor.applyDiscount(0.05);

        assertEquals(95.00, monitor.getDiscountedPrice(), 0.001);
    }

    @Test
    void shouldCapDiscountAtTenPercentBecauseShippingIsNeverDiscounted() {
        monitor.applyDiscount(0.20);

        assertEquals(90.00, monitor.getDiscountedPrice(), 0.001);
    }

    @Test
    void shouldRejectDiscountWhenRateIsAboveTwentyPercent() {
        assertThrows(IllegalArgumentException.class, () -> monitor.applyDiscount(0.21));
    }

    @Test
    void shouldDescribeItselfAsPhysicalWhenSeenAsProduct() {
        Product p = monitor;

        assertEquals("Physical: Monitor | 5.4kg", p.getSummary());
    }

}
