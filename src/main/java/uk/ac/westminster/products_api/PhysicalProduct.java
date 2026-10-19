package uk.ac.westminster.products_api;

// A PhysicalProduct IS-A Product because it is still something the shop sells, with an id, a name and a price - it just also has to be shipped.
// A ShoppingCart would NOT pass this test: a cart is not a product, it HAS-A list of products (composition, not inheritance).

/**
 * A product that is shipped in a box. Adds only the two fields a shipped
 * item needs; id, name and price are inherited from Product.
 */
public class PhysicalProduct extends Product {

    private double weightKg;
    private String dimensions;

    // (a) empty no-arg constructor for Jackson
    public PhysicalProduct() {
    }

    // (b) full constructor - the inherited half is set up by super(...)
    public PhysicalProduct(Long id, String name, double price,
                           double weightKg, String dimensions) {
        super(id, name, price); // MUST be the first statement
        this.weightKg = weightKg;
        this.dimensions = dimensions;
    }

    // (c) getters for the two new fields
    public double getWeightKg() {
        return weightKg;
    }

    public String getDimensions() {
        return dimensions;
    }

}
