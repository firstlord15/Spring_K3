package com.ithub.org.utils;

import com.ithub.org.repositories.OrderRepository;
import com.ithub.org.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean existsById(long id) {
        return orderRepository.existsById(id);
    }

    public List<Order> getAllOrders() {
        log.warn("Get all orders");
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        log.warn("Order found by id: {}", id);
        return orderRepository.findById(id);
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
}
