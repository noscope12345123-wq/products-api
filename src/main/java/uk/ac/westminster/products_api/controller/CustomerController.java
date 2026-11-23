package uk.ac.westminster.products_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import uk.ac.westminster.products_api.model.Address;
import uk.ac.westminster.products_api.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Operation(summary = "Get a sample customer by ID", description = "Returns a fixed sample customer with a nested address, using the id you send. Demo endpoint from Tutorial 2: there is no customer storage yet.")
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        Address address = new Address("115 New Cavendish Street", "London", "W1W 6UW");
        return new Customer(id, "Ada Lovelace", "ada@example.com", address);
    }

}
