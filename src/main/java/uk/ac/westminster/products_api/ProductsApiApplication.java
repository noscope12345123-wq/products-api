package uk.ac.westminster.products_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 5COSC019W Object Oriented Programming - products-api.
 *
 * This is the entry point of the Spring Boot application.
 * Run this class (green Run button / Shift+F10) to start the embedded
 * web server on http://localhost:8080 - Swagger UI is at /swagger-ui.html
 *
 * @OpenAPIDefinition sets the title, version and description shown at
 * the top of Swagger UI (instead of the default "OpenAPI definition v0").
 */
@OpenAPIDefinition(info = @Info(
        title = "Products API",
        version = "1.0",
        description = "A three-layer Spring Boot REST API for a product catalogue: "
                    + "digital, physical and bundle products with polymorphic summaries, "
                    + "discounts and 404 error handling. 5COSC019W Object Oriented Programming, "
                    + "University of Westminster."))
@SpringBootApplication
public class ProductsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApiApplication.class, args);
	}

}
