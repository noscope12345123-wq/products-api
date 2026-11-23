package uk.ac.westminster.products_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Simple text endpoints:
 *   GET /hello    -> a greeting
 *   GET /goodbye  -> a farewell
 *   GET /status   -> a status message including today's date
 */
@RestController
public class HelloController {

    @Operation(summary = "Say hello", description = "Returns a greeting as plain text. Responds 200 OK.")
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @Operation(summary = "Say goodbye", description = "Returns a farewell as plain text. Responds 200 OK.")
    @GetMapping("/goodbye")
    public String goodbye() {
        return "Goodbye from Spring Boot!";
    }

    @Operation(summary = "Check the API is running", description = "Returns a status message with today's date. Responds 200 OK.")
    @GetMapping("/status")
    public String status() {
        return "API is running — Tutorial 1 — " + LocalDate.now().toString();
    }

}
