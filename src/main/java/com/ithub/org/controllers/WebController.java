package com.ithub.org.controllers;

import com.ithub.org.models.Order;
import com.ithub.org.service.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Hidden
@Slf4j
@Controller
@Tag(name = "Web Controller")
public class WebController {

    private final OrderService orderService;

    public WebController(OrderService orderService) {
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
        Order order = orderService.getOrderById(id);

        model.addAttribute("order", order);
        log.info("Opened page with orders by id: {}", id);
        return "order";
    }
}
