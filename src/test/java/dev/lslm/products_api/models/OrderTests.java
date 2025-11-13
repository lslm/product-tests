package dev.lslm.products_api.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTests {
    @Test
    public void shouldReturnTotalPrice() {
        Product product = new Product();
        product.setPrice(100);
        product.setMaxDiscount(0.1);

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(2);
        order.setDiscount(0.1);

        double expectedOrderPrice = order.getOrderPrice();
        assertEquals(180.0, expectedOrderPrice);
    }

    @Test
    public void shouldReturnTotalPriceWithDiscount() {
        Product product = new Product();
        product.setPrice(100);
        product.setMaxDiscount(0.20);

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(2);
        order.setDiscount(0.1);

        double expectedOrderPrice = order.getOrderPrice();
        assertEquals(180.0, expectedOrderPrice);
    }

    @Test
    public void shouldReturnTotalPriceWithMaxDiscountAllowed() {
        Product product = new Product();
        product.setPrice(100);
        product.setMaxDiscount(0.15);

        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(2);
        order.setDiscount(0.5);

        double expectedOrderPrice = order.getOrderPrice();
        assertEquals(170.0, expectedOrderPrice);
    }
}
