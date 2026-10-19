package uk.ac.westminster.products_api;

import java.util.ArrayList;
import java.util.List;

/*
 * IS-A or HAS-A?
 *
 * For IS-A: a bundle is sold exactly like any other product. It has its own
 * id, name and single price, it sits in the same List<Product>, and the same
 * endpoints can list, find and delete it. To the catalogue it IS-A Product.
 *
 * For HAS-A: what makes a bundle a bundle is the list of products inside it,
 * and that part is composition, not inheritance - a bundle HAS products.
 *
 * Verdict: both are true, and the design uses both. extends Product is
 * justified only because a bundle is sold as one item. If bundles were never
 * sold on their own, a plain Bundle class holding a List<Product> (no
 * extends) would be the cleaner design.
 */
public class BundleProduct extends Product {

    private List<Product> items = new ArrayList<>();

    public BundleProduct() {
    }

    public BundleProduct(Long id, String name, double price, List<Product> items) {
        super(id, name, price);
        this.items = items;
    }

    public List<Product> getItems() {
        return items;
    }

}
