package com.ithub.org.utils;

import com.ithub.org.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(OrderService orderService) {
        return args -> {
            orderService.CreateTestOrders();
        };
    }
}
