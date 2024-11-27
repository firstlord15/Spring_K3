package com.ithub.org.models;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность заказов")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Уникальный идентификатор заказа", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Product name can't be null")
    @Schema(description = "Название товаров", example = "Macbook")
    private String product;

    @Min(value = 1, message = "Quantity must be greater than 0")
    @Schema(description = "Количество товаров", example = "10")
    private int quantity; // @NotNull не требуется

    @NotNull(message = "Price can't be null")
    @Positive(message = "Price must be positive")
    @Schema(description = "Цена товаров", example = "100000.00")
    private BigDecimal price;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Статус заказа", example = "DELIVERED")
    private OrderStatus status;

    @NotNull(message = "Order date can't be null")
    @Schema(description = "Дата создания заказа", accessMode = Schema.AccessMode.READ_ONLY)
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