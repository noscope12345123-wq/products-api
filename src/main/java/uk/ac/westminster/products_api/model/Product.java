package uk.ac.westminster.products_api.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The parent of every product the shop sells.
 *
 * It is ABSTRACT: a "plain product" has no meaning, so nobody can write
 * new Product(...). Every concrete subclass must say how it describes
 * itself by overriding getSummary().
 *
 * The fields are private and the setters validate, so an object can never
 * hold a negative price or an empty name.
 *
 * The two Jackson annotations tell the JSON converter which subclass an
 * object is. Every product in a response gets a "type" field ("digital",
 * "physical" or "bundle"), and a POST body MUST send one of them, because
 * Jackson cannot build an abstract Product.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DigitalProduct.class, name = "digital"),
        @JsonSubTypes.Type(value = PhysicalProduct.class, name = "physical"),
        @JsonSubTypes.Type(value = BundleProduct.class, name = "bundle")
})
public abstract class Product {

    private Long id;
    private String name;
    private double price;

    // Still needed: Jackson uses the subclasses' no-arg constructors, which call this one.
    protected Product() {
    }

    protected Product(Long id, String name, double price) {
        this.id = id;
        this.name = checkName(name);
        this.price = checkPrice(price);
    }

    // No body, no default. Product cannot answer this meaningfully,
    // so every concrete subclass MUST answer it.
    public abstract String getSummary();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = checkName(name);
    }

    public void setPrice(double price) {
        this.price = checkPrice(price);
    }

    // Private helpers, so the constructor never calls an overridable method.
    private static String checkName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        return name;
    }

    private static double checkPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        return price;
    }

}
