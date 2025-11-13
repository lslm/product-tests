package dev.lslm.products_api.services;

import dev.lslm.products_api.models.Order;
import dev.lslm.products_api.models.Product;
import dev.lslm.products_api.models.Stock;
import dev.lslm.products_api.repositories.OrderRepository;
import dev.lslm.products_api.repositories.ProductRepository;
import dev.lslm.products_api.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository,
                        StockRepository stockRepository,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(int productId, int quantity, double discount) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Product Product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        // Normalize discount
        if (discount < 0) discount = 0.0;
        if (discount > Product.getMaxDiscount()) {
            discount = Product.getMaxDiscount();
        }

        Stock stock = stockRepository.findByProduct(Product)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found for product: " + productId));

        if (stock.getQuantity() < quantity) {
            throw new IllegalStateException("Insufficient stock. Available=" + stock.getQuantity() + ", requested=" + quantity);
        }

        // Decrease stock
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        // Create and persist order
        Order order = new Order();
        order.setProduct(Product);
        order.setQuantity(quantity);
        order.setDiscount(discount);

        return orderRepository.save(order);
    }
}
