package com.ithub.org;

import com.ithub.org.models.Order;
import com.ithub.org.repositories.OrderRepository;
import com.ithub.org.testing.Tests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderRepositoryTest extends Tests {

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        createTestOrder();
    }

    @Test
    public void testGetAllOrders() {
        Assertions.assertFalse(orderRepository.findAll().isEmpty(), "Expected no orders, but found some.");
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    public void testExistsById(long id) {
        Assertions.assertTrue(orderRepository.existsById(id));
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    public void testDeleteOrder(long id) {
        orderRepository.deleteById(id);
        Assertions.assertFalse(orderRepository.findById(id).isPresent());
    }

    @ParameterizedTest
    @MethodSource("orderProviderWithId")
    public void testUpdateById(long id, Order newOrder) {
        orderRepository.findById(id).map(order -> {
            order.setOrderDate(newOrder.getOrderDate());
            order.setStatus(newOrder.getStatus());
            order.setProduct(newOrder.getProduct());
            order.setQuantity(newOrder.getQuantity());
            order.setPrice(newOrder.getPrice());
            return orderRepository.save(order);
        });
    }
}
