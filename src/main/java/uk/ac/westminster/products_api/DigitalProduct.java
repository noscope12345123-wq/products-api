package uk.ac.westminster.products_api;

/**
 * A product that is downloaded rather than shipped.
 *
 * id, name and price are NOT declared here: they live in Product and are
 * inherited, together with getId(), getName() and getPrice(). This class
 * only adds what a download needs.
 */
public class DigitalProduct extends Product {

    private String downloadUrl;
    private double fileSizeMB;

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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

}
