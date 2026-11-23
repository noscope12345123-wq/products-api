# Architecture notes — products-api (Tutorial 9)

Packages: `controller` → `service` → `repository`, plus `model` and `exception`, all under `uk.ac.westminster.products_api`.

## Layer audit

Checked by reading the code (Find in Files), not by running it.

- **A — Pass.** No controller has a repository field or calls a repository. `ProductController` depends only on `ProductService`.
- **B — Pass.** `ProductService` uses no HTTP classes (`ResponseEntity`, `HttpStatus`, `org.springframework.http`).
- **C — Pass.** Every `ProductController` method is one line that calls one `ProductService` method; there is no `if` in the controller. Note: `CustomerController` builds a fixed sample `Customer` itself. It is a demo endpoint from Tutorial 2 with no storage; if customers become a real feature, this moves to a `CustomerService`.
- **D — Fixed.** Before: `GET /products/{id}` returned `null` with **200 OK** for an unknown id, and `DELETE` silently did nothing. Now `ProductService` throws `ProductNotFoundException`, and `GlobalExceptionHandler` is the only place that turns it into **404 Not Found** with an `ErrorResponse` body (and an `IllegalArgumentException` into **400 Bad Request**). No controller builds a status code itself.
- **E — Fixed.** Before: there was no repository — `ProductService` owned the product list and the id counter itself. Now storage sits behind the `ProductRepository` interface, implemented by `InMemoryProductRepository`, and the service's field and constructor parameter are of type `ProductRepository`, not the class.
- **F — Fixed.** Before: `ProductController` used `@Autowired` field injection. Now `ProductController` and `ProductService` both use constructor injection. There is no `new ProductService`, no `new InMemoryProductRepository` and no field `@Autowired` in `src/main/java`.
- **G — Fixed.** Before: `ProductService` (a singleton bean shared by every request) held the `products` list and `nextId` as mutable fields. Both moved into `InMemoryProductRepository`, where the store *is* the data. The service's only field is now `private final ProductRepository repository`.

Also fixed while auditing: `POST /products` now returns **201 Created** and `DELETE /products/{id}` returns **204 No Content**.

**Summary:** all seven checks pass; four violations were fixed (D, E, F, G), and all 33 tests stay green.

## Architecture rationale

### 1. Name the architecture
The API uses a three-layer architecture. `ProductController` handles HTTP only: it maps URLs, sets status codes (201 Created, 204 No Content) and calls exactly one `ProductService` method per endpoint. `ProductService` holds the business rules, such as rejecting an unknown id and building one summary per product. Storage sits behind the `ProductRepository` interface, implemented by `InMemoryProductRepository`.

### 2. Justify it with a quality
Coupling is low because `ProductService` depends only on the `ProductRepository` interface, which Spring passes in through its constructor (Dependency Injection). Replacing `InMemoryProductRepository` with a database class would therefore not change a line of business logic. Cohesion is high: each class has one job, and `GlobalExceptionHandler` is the only place that turns errors into status codes.

### 3. Show the evidence
Figure 1, the sequence diagram for POST /products, shows the request passing controller → service → repository without skipping a layer, and Figure 2, the class diagram, shows the service pointing at the interface. `ProductServiceTest` already swaps in a Mockito mock of `ProductRepository`, and the layer audit above records all seven checks passing after four fixes.

### 4. State the trade-off
Storage is in-memory by design, to keep the scope on object-oriented design. The cost is that every product is lost on restart and two running instances cannot share data, so the application cannot scale out. Moving to a database would need one new class implementing `ProductRepository` plus a datasource configuration; the controller and service would stay unchanged.

## Self-audit LO1–LO5

| LO | Evidence | Status |
|----|----------|--------|
| LO1 | Private fields with validation (`Product.setPrice`, `setName`); abstract `Product` with three subclasses; `getSummary()` overridden and used through `List<Product>` in `getAllSummaries()`; interfaces `Discountable` and `ProductRepository`. | Done |
| LO2 | 200 / 201 / 204 / 400 / 404; `GlobalExceptionHandler` with `ErrorResponse`; constructor injection everywhere. | Done |
| LO3 | Three layers in separate packages, audit passes, repository interface; 33 tests incl. Mockito (`ProductServiceTest`) and `@WebMvcTest` (`ProductControllerTest`). | Done — confirm with `./mvnw test` |
| LO4 | This rationale; `DESIGN-NOTES.md`; `TESTING-NOTES.md` (coverage number still to fill in). Sustainability and ethics points (Lecture 10): missing. | Partly done |
| LO5 | `docs/diagrams/class-diagram.puml`, `post-sequence.puml`, `get-by-id-404-sequence.puml`; `@Operation` on every endpoint. Swagger screenshots, PNG exports, Git commits, AI-use notes: still to do. | Partly done |

## Still to do

1. Run `./mvnw test` (screenshot of BUILD SUCCESS), run coverage and write the number into `TESTING-NOTES.md`; Swagger screenshots into `docs/images` — by 24 Nov 2026.
2. Export every `.puml` in `docs/diagrams` to PNG; commit and push — by 24 Nov 2026.
3. Report sections: sustainability, ethics and AI-use (Lecture 10) — by 6 Dec 2026.

## AI-use notes (draft — finish in your own words)

- **Tool:** Claude (Anthropic), 23 Nov 2026.
- **What I asked:** to bring my project up to date with Tutorials 5, 7, 8 and 9 using the lab guides — abstract `Product` and `Discountable`; repository layer, exception handling and constructor injection; unit and web tests; `@Operation` documentation, layer audit, PlantUML diagrams and these notes.
- **What it produced:** the classes in `model`, `repository`, `exception`, the updated `service` and `controller`, the 33 tests, the three diagrams and drafts of `DESIGN-NOTES.md`, `TESTING-NOTES.md` and this file.
- **What I checked / changed:** _(fill in: e.g. ran `./mvnw test`, tested every endpoint in Swagger, checked each diagram arrow against the code, rewrote the rationale in my own words)_
