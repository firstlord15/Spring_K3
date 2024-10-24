package com.ithub.org.utils;

import com.ithub.org.repository.OrderRepository;
import com.ithub.org.models.Order;
import com.ithub.org.models.OrderStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(OrderRepository orderRepository) {
        return args -> {
            if (orderRepository.count() == 0) {
                orderRepository.save(new Order("Laptop", 7, new BigDecimal("145000.00"), OrderStatus.CREATED, LocalDate.now()));
                orderRepository.save(new Order("Phone", 25, new BigDecimal("60399.00"), OrderStatus.SHIPPED, LocalDate.of(2024, 10, 20)));
                orderRepository.save(new Order("Computer", 10, new BigDecimal("198000.00"), OrderStatus.DELIVERED, LocalDate.now()));
                orderRepository.save(new Order("Mouse", 15, new BigDecimal("4999.00"), OrderStatus.CREATED, LocalDate.now()));
            }
        };
    }
}
