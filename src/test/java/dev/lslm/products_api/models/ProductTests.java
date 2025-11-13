package dev.lslm.products_api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTests {
    @Test
    public void shouldCalculatePriceWithDiscount() {
        Product product = new Product();
        product.setPrice(100.0);
        product.setMaxDiscount(0.2);
        double totalPrice = product.getPriceWithDiscount(0.2);
        assertEquals(80, totalPrice);
    }

    @Test
    public void shouldCalculatePriceWithMaxDiscountAllowed() {
        Product product = new Product();
        product.setPrice(100.0);
        product.setMaxDiscount(0.2);
        double totalPrice = product.getPriceWithDiscount(0.5);
        assertEquals(80, totalPrice);
    }
}
