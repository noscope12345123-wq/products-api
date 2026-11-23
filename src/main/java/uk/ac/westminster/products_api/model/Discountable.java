package uk.ac.westminster.products_api.model;

/**
 * A contract: anything that can be discounted.
 *
 * No bodies, no fields, no constructor. Every implementing class promises
 * to provide both methods but decides for itself how they behave.
 *
 * The rule every implementation follows: a rate from 0 to 0.20 (0-20%)
 * is accepted; anything else throws IllegalArgumentException and leaves
 * the price unchanged.
 */
public interface Discountable {

    double MAX_DISCOUNT_RATE = 0.20;

    void applyDiscount(double rate);

    double getDiscountedPrice();

}
