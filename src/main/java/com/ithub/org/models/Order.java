package com.ithub.org.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name can't be null")
    private String product;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity; // @NotNull не требуется

    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @NotNull(message = "Order date can't be null")
    private LocalDate orderDate;

    public Order(String product, int quantity, BigDecimal price, OrderStatus status, LocalDate orderDate) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order(Long id, String product, int quantity, BigDecimal price, OrderStatus status, LocalDate orderDate) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Order() {}
}