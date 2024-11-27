package com.ithub.org.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ithub.org.models.Order;
import com.ithub.org.models.OrderStatus;
import com.ithub.org.service.OrderService;
import lombok.Getter;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

@Getter
abstract public class Tests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    public void createTestOrder() {
        orderService.CreateTestOrders();
    }

    // Метод-провайдер для предоставления значений ID
    private static Stream<Long> idProvider() {
        return Stream.of(1L, 2L);
    }

    // Метод-провайдер для предоставления значений Order.class
    static Stream<Arguments> orderProviderWithId() {
        return Stream.of(
                Arguments.of(1L, new Order("test name product", 1, new BigDecimal("1000.00"), OrderStatus.CREATED, LocalDate.now())),
                Arguments.of(2L, new Order("another product", 2, new BigDecimal("500.00"), OrderStatus.SHIPPED, LocalDate.now().minusDays(2)))
        );
    }

    // Метод-провайдер для предоставления значений Order
    static Stream<Order> orderProvider() {
        return Stream.of(
                new Order("test name product", 1, new BigDecimal("1000.00"), OrderStatus.CREATED, LocalDate.now()),
                new Order("another product", 2, new BigDecimal("500.00"), OrderStatus.SHIPPED, LocalDate.now().minusDays(2))
        );
    }
}
