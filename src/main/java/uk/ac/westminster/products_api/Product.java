package uk.ac.westminster.products_api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * An encapsulated product.
 *
 * The fields are private, so nothing outside the class can change them
 * directly. Spring's JSON converter (Jackson) builds the response from
 * the public getters: getId() -> "id", getName() -> "name",
 * getPrice() -> "price". Without the getters the response would be {}.
 *
 * The setters are used by ProductService (setId) and by Jackson, which
 * builds a Product from the JSON body of a POST request.
 *
 * The three Jackson annotations tell the JSON converter which subclass
 * an object is. Every product in a response gets a "type" field
 * ("product", "digital" or "physical"), and a POST body can send
 * "type": "digital" so that Jackson builds a DigitalProduct instead of
 * a plain Product. Without "type", a plain Product is created.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", defaultImpl = Product.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DigitalProduct.class, name = "digital"),
        @JsonSubTypes.Type(value = PhysicalProduct.class, name = "physical")
})
@JsonTypeName("product")
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
