package uk.ac.westminster.products_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A second controller, so Swagger UI shows endpoints grouped
 * under two separate controllers.
 */
@RestController
public class InfoController {

    @GetMapping("/info")
    public String info() {
        return "products-api: a Spring Boot REST API for the 5COSC019W "
                + "Object Oriented Programming tutorials (University of Westminster).";
    }

}
