package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    private Long orderId;

    @BeforeEach
    public void setup() {
        orderRepository.deleteAll();
        Order testOrder = new Order(new Date());
        Order savedOrder = orderRepository.save(testOrder);
        orderId = savedOrder.getOrderId();
    }

    @Test
    public void getAllOrders_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createOrder_ShouldReturnCreatedOrder() throws Exception {
        Order newOrder = new Order(new Date());
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderDate", is(notNullValue())));
    }

    @Test
    public void getOrderById_ShouldReturnOrder() throws Exception {
        mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(orderId.intValue())));
    }

    @Test
    public void updateOrder_ShouldReturnUpdatedOrder() throws Exception {
        Order updatedOrder = new Order(new Date());
        mockMvc.perform(put("/orders/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderDate", is(notNullValue())));
    }

    @Test
    public void deleteOrder_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/orders/" + orderId))
                .andExpect(status().isOk());
    }
}
