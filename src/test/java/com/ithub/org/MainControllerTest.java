package com.ithub.org;

import com.ithub.org.controllers.ApiController;
import com.ithub.org.models.Order;
import com.ithub.org.testing.Tests;
import com.ithub.org.utils.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiController.class)
public class MainControllerTest extends Tests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private ApiController mainController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/api/orders")).andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("idProvider")
    public void testGetOrderById(long id) throws Exception {
        Order order = new Order();
        order.setId(id);
        order.setProduct("Test Order");

        when(orderService.getOrderById(id)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/order/" + id)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("orderProvider")
    public void testCreateOrder(Order order) throws Exception {
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);
        String orderJson = getObjectMapper().writeValueAsString(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isCreated());
    }
}