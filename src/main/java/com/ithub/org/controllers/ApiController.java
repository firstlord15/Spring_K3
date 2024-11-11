package com.ithub.org.controllers;

import com.ithub.org.exceptionHandler.ResourceNotFoundException;
import com.ithub.org.models.Order;
import com.ithub.org.utils.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@Slf4j
@RestController
public class ApiController {

    private final OrderService orderService;

    public ApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    // API requests
    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orderList = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found by id: " + id));
    }

    @PostMapping("/api/orders")
    public ResponseEntity<Order> newOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        log.info("Created order: {}", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order newOrder) {
        return orderService.updateById(id, newOrder)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Order not found by id {0} or cannot update this order", id)));
    }

    @DeleteMapping("/api/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        if (!orderService.existsById(id)) {
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }

        orderService.deleteOrder(id);
        log.info("Order under id {} deleted", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}