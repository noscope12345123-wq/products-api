# Design notes — products-api

## Tutorial 5 — polymorphism and abstraction

- `Product` is **abstract** with one abstract method, `getSummary()`. Nobody can create a "plain product"; `DigitalProduct`, `PhysicalProduct` and `BundleProduct` each override it with `@Override`.
- `ProductService.getAllSummaries()` loops over a `List<Product>` and calls `p.getSummary()` with no `instanceof` and no casts, so `GET /products/summaries` shows one summary per product, each written by a different class. Adding `BundleProduct` changed zero lines of that method (Open/Closed Principle).
- `Discountable` is an interface with two methods. `DigitalProduct` and `PhysicalProduct` both implement it with the same contract (a rate from 0 to 0.20, otherwise `IllegalArgumentException`) but different rules: a digital product takes the full rate, while a physical product is capped at 10% because shipping is never discounted.
- Because `Product` is abstract, Jackson cannot build one from JSON on its own. The `@JsonTypeInfo` / `@JsonSubTypes` annotations on `Product` solve this: a POST body must carry `"type": "digital"`, `"physical"` or `"bundle"`.

## Tutorial 7 — the repository layer (LO4)

(a) **Modularity:** I applied the Dependency Inversion Principle — `ProductService` depends on the `ProductRepository` interface (`repository/ProductRepository.java`) and receives it through its constructor, never on `InMemoryProductRepository`.

(b) **Testability:** because the service only knows the interface, `ProductServiceTest` replaces the repository with a Mockito mock and tests every business rule without Spring, a server or real storage — something that was impossible while `ProductService` owned its own `List<Product>`.

(c) **The swap test:** moving to a database would mean writing one new class that implements `ProductRepository` (for example `JdbcProductRepository`); `ProductService`, `ProductController` and every test in `ProductServiceTest` would not change.

Task 19 sentence: *both versions run today, but the interface version is better because the day storage changes, only the repository class changes — with the concrete type, the service would have to change too.*

Task 21 sentence: *a product added with POST is still there on the next GET because Spring creates one `InMemoryProductRepository` (a singleton) shared by every request.*
