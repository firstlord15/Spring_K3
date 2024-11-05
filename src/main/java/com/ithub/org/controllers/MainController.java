package com.ithub.org.controllers;

import com.ithub.org.exceptionHandler.ResourceNotFoundException;
import com.ithub.org.models.Order;
import com.ithub.org.utils.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MainController {

    private final OrderService orderService;

    public MainController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Getting Started: Serving Web Content");
        log.info("The home page is open");
        return "index";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("title", "Orders Page");
        List<Order> orders = orderService.getAllOrders();

        if (!orders.isEmpty()) {
            model.addAttribute("orders", orders);
            log.info("The order page is open");
        } else {
            model.addAttribute("errorMessage", "No orders found.");
            log.error("No orders found.");
        }
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") long id, Model model){
        model.addAttribute("title", "Page by " + id);
        Optional<Order> order = orderService.getOrderById(id);

        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            log.info("Opened page with orders by id: {}", id);
        } else {
            model.addAttribute("errorMessage", "No orders found.");
            log.error("No orders found by id: {}", id);
        }
        return "order";
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