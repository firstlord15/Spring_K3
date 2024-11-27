package com.ithub.org.controllers;

import com.ithub.org.exceptionHandler.ResourceNotFoundException;
import com.ithub.org.models.Order;
import com.ithub.org.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@Tag(name = "API Controller")
@Slf4j
@RestController
public class ApiController {

    private final OrderService orderService;

    public ApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    // API requests
    @Operation(description = "Позволяет выводить заказы")
    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orderList = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @Operation(description = "Позволяет выводить заказы по id")
    @GetMapping("/api/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(description = "Позволяет создавать новые заказы")
    @PostMapping("/api/orders")
    public ResponseEntity<Order> newOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        log.info("Created order: {}", order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @Operation(description = "Позволяет обновить данные заказов по id")
    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order newOrder) {
        return orderService.updateById(id, newOrder)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Order not found by id {0} or cannot update this order", id)));
    }

    @Operation(description = "Позволяет удалить заказы по id")
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