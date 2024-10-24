package com.ithub.org.utils;

import com.ithub.org.models.Order;
import com.ithub.org.models.OrderStatus;
import com.ithub.org.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock // создается mock объект, который работает с заглушкой, то есть реальной работы или данных он не получит и не сделает
    private OrderRepository orderRepository;

    @InjectMocks // тот же mock объект только еще в него автоматически внедряют объекты помеченые @Mock
    private OrderService orderService;

    @BeforeEach // метод запускающийся при каждом тесте и инициализарующий аннотации @Mock и @InjectMocks
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test // сами тесты
    public void testSaveOrder() {
        // Arrange
        Order order = new Order("Laptop", 10, new BigDecimal("1000"), OrderStatus.CREATED, LocalDate.now());
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order savedOrder = orderService.saveOrder(order);

        // Assert
        assertNotNull(savedOrder);
        assertEquals("Laptop", savedOrder.getProduct());
        verify(orderRepository, times(1)).save(order);
    }
}
