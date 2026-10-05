package uk.ac.westminster.products_api;

/**
 * An encapsulated product.
 *
 * The fields are private, so nothing outside the class can change them
 * directly. Spring's JSON converter (Jackson) builds the response from
 * the public getters: getId() -> "id", getName() -> "name",
 * getPrice() -> "price". Without the getters the response would be {}.
 */
public class Product {

    private Long id;
    private String name;
    private double price;

    public Product() {
    }

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

}
