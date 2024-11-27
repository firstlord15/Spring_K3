package com.ithub.org.service;

import com.ithub.org.exceptionHandler.ResourceNotFoundException;
import com.ithub.org.models.Order;
import com.ithub.org.models.OrderStatus;
import com.ithub.org.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {
    private final JdbcTemplate jdbcTemplate;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, JdbcTemplate jdbcTemplate) {
        this.orderRepository = orderRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public long count () {
        return orderRepository.count();
    }

    public boolean existsById(long id) {
        return orderRepository.existsById(id);
    }

    public List<Order> getAllOrders() {
        log.warn("Get all orders");
        return orderRepository.findAll();
    }

    public Order getOrderById(long id) {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found by ID: {}", id);
                    return new ResourceNotFoundException("Item not found by id: " + id);
                });
    }

    public Optional<Order> updateById(long id, Order newOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderDate(newOrder.getOrderDate());
            order.setStatus(newOrder.getStatus());
            order.setProduct(newOrder.getProduct());
            order.setQuantity(newOrder.getQuantity());
            order.setPrice(newOrder.getPrice());
            log.warn("Order updated: {}", order);
            return orderRepository.save(order);
        });
    }

    public Order saveOrder(Order order) {
        log.warn("Created order: {}", order);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        log.warn("Deleted order id: {}", id);
        orderRepository.deleteById(id);
    }

    public void CreateTestOrders() {
        jdbcTemplate.execute("TRUNCATE TABLE orders RESTART IDENTITY");
        orderRepository.deleteAll();
        orderRepository.save(new Order("Laptop", 7, new BigDecimal("145000.00"), OrderStatus.CREATED, LocalDate.now()));
        orderRepository.save(new Order("Phone", 25, new BigDecimal("60399.00"), OrderStatus.SHIPPED, LocalDate.of(2024, 10, 20)));
        orderRepository.save(new Order("Computer", 10, new BigDecimal("198000.00"), OrderStatus.DELIVERED, LocalDate.now()));
        orderRepository.save(new Order("Mouse", 15, new BigDecimal("4999.00"), OrderStatus.CREATED, LocalDate.now()));
    }
}
