package com.ithub.org.testing;

import com.ithub.org.models.Order;
import com.ithub.org.utils.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;


@SpringBootTest
public class OrderServiceTest extends Tests {

    @Autowired
    private OrderService orderService;

    @Test
    public void testGetAllOrders() {
        assertTrue(!orderService.getAllOrders().isEmpty(), "Expected no orders, but found some.");
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    public void testExistsById(long id) {
        orderService.existsById(id);
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    public void testDeleteOrder(long id) {
        orderService.deleteOrder(id);
    }

    @ParameterizedTest
    @MethodSource("orderProviderWithId")
    public void testUpdateById(long id, Order newOrder) {
        orderService.updateById(id, newOrder);
    }
}
