# Testing notes — products-api

Run all tests the way the marker will: `./mvnw test` (Windows: `mvnw.cmd test`). 33 tests in 6 classes.

| Test class | Tests | What it covers |
|---|---|---|
| `model/DigitalProductTest` | 11 | price rule (valid, negative, zero), empty name, discount boundaries (0, 0.10, 0.20, -0.01, 0.21, price kept after a rejected discount), polymorphic `getSummary()` |
| `model/PhysicalProductTest` | 4 | discount below the 10% shipping cap, cap applied at 0.20, 0.21 rejected, summary |
| `service/ProductServiceTest` | 8 | Mockito: find all, find by id (found / not found), save with `verify`, delete found with `verify`, delete missing with `verify(never())`, summaries, search by name |
| `repository/InMemoryProductRepositoryTest` | 4 | id assigned on save, id kept on update, empty `Optional` for unknown id, delete |
| `controller/ProductControllerTest` | 5 | `@WebMvcTest`: 200 + JSON body, 404 + `ErrorResponse` body, 201 on POST, 204 on DELETE, 404 on DELETE of a missing product |
| `ProductsApiApplicationTests` | 1 | the whole application context starts |

## Three sentences (LO4)

(a) **What and why:** `DigitalProductTest` and `PhysicalProductTest` test the two business rules at their exact boundaries — a price below 0 and a discount outside 0–0.20 are rejected — `ProductServiceTest` has a success and a failure test for every service method, and `ProductControllerTest` proves the status codes 200, 201, 204 and 404 without starting a server.

(b) **Why the repository is mocked:** in `ProductServiceTest` the `ProductRepository` is a Mockito mock, so a failing test points at a rule in `ProductService`, never at storage, and `verify(repository, never()).delete(any())` proves that deleting a missing product never reaches the repository.

(c) **Coverage:** IntelliJ's coverage report shows `ProductService` at **100% of lines (19/19) and 83% of branches (5/6)** and `InMemoryProductRepository` at 100% — the repository needs its own test class because the mock in `ProductServiceTest` never runs its real code. Overall line coverage is 57%, pulled down by code with no business logic that is deliberately not tested: the demo classes `Customer`, `Person` and `Address`, `BundleProduct`, and the `HelloController`, `InfoController` and `CustomerController` endpoints.

## Bugs found by tests

- `shouldRejectNegativePrice` and `shouldRejectDiscountWhenRateIsAboveTwentyPercent` could only pass after the validation was added to `Product.setPrice` and `applyDiscount` — before Tutorial 8 neither rule existed.
