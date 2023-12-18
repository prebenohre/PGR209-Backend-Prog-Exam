package no.kristiania.ordersystemformachinefactory.E2ETests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostingToCustomersAndRetrievingAddressE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    void testCreateCustomer() throws Exception {
        // 01. Create a customer and address object
        String customerJson = "{ \"customer\": { \"customerEmail\": \"John@John.com\", \"customerName\": \"John\"}, \"address\": { \"houseNumber\": \"91856\", \"street\": \"Darin Ridge\", \"city\": \"Shanelland\", \"postalCode\": \"83469\", \"country\": \"Yemen\" }}";

        // 02. Send a POST request to the API endpoint to add customer with address
        MvcResult result = mockMvc.perform(post("/customers/createWithAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        //03. Get customerId from response
        Long customerId = objectMapper.readTree(responseBody).get("customerId").asLong();

        //04. Check that the customer is created correctly
        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"));

        //04. Check that the address is created correctly
        mockMvc.perform(get("/customers/{id}/addresses", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Shanelland"));
    }
}


