package uk.ac.westminster.products_api.model;

// A PhysicalProduct IS-A Product because it is still something the shop sells, with an id, a name and a price - it just also has to be shipped.
// A ShoppingCart would NOT pass this test: a cart is not a product, it HAS-A list of products (composition, not inheritance).

/**
 * A product that is shipped in a box. Same Discountable contract as
 * DigitalProduct, deliberately different rule: shipping is never
 * discounted, so the reduction is capped at 10%.
 */
public class PhysicalProduct extends Product implements Discountable {

    // Shipping costs real money, so a physical item never loses more than 10%.
    public static final double SHIPPING_CAP = 0.10;

    private double weightKg;
    private String dimensions;
    private double discountRate; // 0 until a discount is applied

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

    @Override
    public String getSummary() {
        return "Physical: " + getName() + " | " + weightKg + "kg";
    }

    @Override
    public void applyDiscount(double rate) {
        if (rate < 0 || rate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount must be between 0 and 20%");
        }
        this.discountRate = Math.min(rate, SHIPPING_CAP);
    }

    @Override
    public double getDiscountedPrice() {
        return getPrice() * (1 - discountRate);
    }

    // (c) getters and setters for the two new fields
    public double getWeightKg() {
        return weightKg;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

}
