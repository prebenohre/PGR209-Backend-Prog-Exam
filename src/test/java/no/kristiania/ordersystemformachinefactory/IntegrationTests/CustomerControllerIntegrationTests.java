package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllCustomers_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createAndGetCustomerById_ShouldReturnCustomer() throws Exception {
        Customer newCustomer = new Customer("Test Customer", "test@customer.com");

        MvcResult result = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName", is(newCustomer.getCustomerName())))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        Customer createdCustomer = objectMapper.readValue(responseString, Customer.class);

        mockMvc.perform(get("/customers/" + createdCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is(createdCustomer.getCustomerId().intValue())))
                .andExpect(jsonPath("$.customerName", is(newCustomer.getCustomerName())));
    }

    @Test
    public void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        long customerId = 1L; // Anta at denne ID-en finnes i databasen
        Customer updatedCustomer = new Customer("Updated Customer", "updated@customer.com");
        mockMvc.perform(put("/customers/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName", is(updatedCustomer.getCustomerName())));
    }

    @Test
    public void deleteCustomer_ShouldReturnOk() throws Exception {
        long customerId = 1L; // Anta at denne ID-en finnes i databasen
        mockMvc.perform(delete("/customers/" + customerId))
                .andExpect(status().isOk());
    }
}

