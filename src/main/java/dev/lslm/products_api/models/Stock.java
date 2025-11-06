package dev.lslm.products_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Stock {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false, name = "quantity")
    private int quantity;
}
