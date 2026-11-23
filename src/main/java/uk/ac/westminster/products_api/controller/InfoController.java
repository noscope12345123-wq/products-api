package uk.ac.westminster.products_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A second controller, so Swagger UI shows endpoints grouped
 * under two separate controllers.
 */
@RestController
public class InfoController {

    @Operation(summary = "Get API information", description = "Returns a one-line description of this API. Responds 200 OK.")
    @GetMapping("/info")
    public String info() {
        return "products-api: a Spring Boot REST API for the 5COSC019W "
                + "Object Oriented Programming tutorials (University of Westminster).";
    }

}
