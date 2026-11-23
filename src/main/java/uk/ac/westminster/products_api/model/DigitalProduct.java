package uk.ac.westminster.products_api.model;

/**
 * A product that is downloaded rather than shipped.
 *
 * id, name and price are inherited from Product. This class adds what a
 * download needs, says how it describes itself (getSummary) and how it is
 * discounted (Discountable).
 */
public class DigitalProduct extends Product implements Discountable {

    private String downloadUrl;
    private double fileSizeMB;
    private double discountRate; // 0 until a discount is applied

    // no-arg constructor - Jackson needs this to build
    // an object from an incoming JSON request body
    public DigitalProduct() {
    }

    public DigitalProduct(Long id, String name, double price,
                          String downloadUrl, double fileSizeMB) {
        super(id, name, price); // MUST be the first statement
        this.downloadUrl = downloadUrl;
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public String getSummary() {
        // getName(), not name - name is private to Product.
        return "Digital: " + getName() + " | Download: " + downloadUrl;
    }

    @Override
    public void applyDiscount(double rate) {
        if (rate < 0 || rate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("Discount must be between 0 and 20%");
        }
        // Nothing is shipped, so the full rate applies.
        this.discountRate = rate;
    }

    @Override
    public double getDiscountedPrice() {
        return getPrice() * (1 - discountRate);
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }

}
