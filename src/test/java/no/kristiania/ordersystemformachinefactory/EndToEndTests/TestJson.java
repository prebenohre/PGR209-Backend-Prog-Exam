package no.kristiania.ordersystemformachinefactory.EndToEndTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestJson {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @Disabled("Prøver å lage en annen")
    @Test
    void testCreateCustomer() throws Exception {
        mockMvc.perform(post("/customers/createWithAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"customer\":{\n" +
                                "        \"customerEmail\": \"John@John.com\",\n" +
                                "        \"customerName\": \"John\"\n" +
                                "    },\n" +
                                "    \"address\":{\n" +
                                "        \"houseNumber\": \"91856\",\n" +
                                "        \"street\": \"Darin Ridge\",\n" +
                                "        \"city\": \"Shanelland\",\n" +
                                "        \"postalCode\": \"83469\",\n" +
                                "        \"country\": \"Yemen\"\n" +
                                "    }\n" +
                                "}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/customers/{id}", 0L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"))
                .andExpect(jsonPath("$.customerEmail").value("John@John.com"));

        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers in database: " + customers);
    }
}
