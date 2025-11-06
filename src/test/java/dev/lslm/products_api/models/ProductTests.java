package dev.lslm.products_api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTests {
    @Test
    public void shouldCalculateTotalPriceWithDiscount() {
        Product product = new Product();
        product.setPrice(100.00);
        product.setMaxDiscount(0.2);
        double priceWithDiscount = product.getPriceWithDiscount(0.1);
        assertEquals(90, priceWithDiscount);
    }

    @Test
    public void shouldCalculatePriceWithMaxDiscount() {
        Product product = new Product();
        product.setPrice(100.0);
        product.setMaxDiscount(0.2);
        double priceWithDiscount = product.getPriceWithDiscount(0.3);
        assertEquals(80, priceWithDiscount);
    }
}
