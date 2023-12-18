package no.kristiania.ordersystemformachinefactory.EndToEndTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreatingAndReceivingCustomer {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testCreateAndRetrieveCustomer() throws Exception {
        // Create a customer object
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName("John Doe");
        newCustomer.setCustomerEmail("john.doe@example.com");

        // Make a post to the API endpoint to add customer
        ResultActions createResult = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isOk());

        // After POST has returned customer, this receives the return value
        Customer createdCustomer = objectMapper.readValue(
                createResult.andReturn().getResponse().getContentAsString(),
                Customer.class);

        // Sends a get request to createdCustomer endpoint with its id to check if it is the same name and email
        mockMvc.perform(get("/customers/{id}", createdCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.customerEmail").value("john.doe@example.com"));
    }
}
