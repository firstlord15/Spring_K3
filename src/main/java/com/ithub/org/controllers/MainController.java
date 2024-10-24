package com.ithub.org.controllers;

import com.ithub.org.exceptionHandler.ResourceNotFoundException;
import com.ithub.org.utils.OrderService;
import com.ithub.org.models.Order;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Controller

public class MainController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Getting Started: Serving Web Content");
        return "index";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("title", "Orders Page");
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) model.addAttribute("errorMessage", "No orders found.");
        else model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable("id") long id, Model model){
        model.addAttribute("title", "Page by " + id);
        Optional<Order> order = orderService.getOrderById(id);

        if (order.isEmpty()) model.addAttribute("errorMessage", "No orders found.");
        else model.addAttribute("order", order.get());
        return "order";
    }

    // API requests
    @PostMapping("/api/orders")
    public ResponseEntity<Order> newOrder(@Valid @RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    @GetMapping("/api/orders")
    @ResponseBody
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/api/order/{id}")
    @ResponseBody
    public ResponseEntity<Order> getOrder(@PathVariable("id") long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found by id: " + id));
    }

    @PutMapping("/api/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @Valid @RequestBody Order newOrder) {
        return orderService.updateById(id, newOrder)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Order not found by id {0} or cannot update this order", id)));
    }

    @DeleteMapping("/api/order/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        if (!orderService.existsById(id)) {
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }

        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}