package no.kristiania.ordersystemformachinefactory.EndToEndTests;

import com.fasterxml.jackson.databind.JsonNode;
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
public class PostingToCustomersAndRetrievingAddress {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }



    @Test
    void testCreateCustomer() throws Exception {
        MvcResult result = mockMvc.perform(post("/customers/createWithAddress")
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
                .andExpect(status().isOk())
                .andDo(result1 -> {
                    System.out.println(result1.getResponse().getContentAsString());
                })
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long customerId = jsonNode.get("customerId").asLong();

        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"))
                .andReturn();

        mockMvc.perform(get("/customers/{id}/addresses", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Shanelland"))
                .andReturn();

    }

    @Test
    void testCreateCustomerGetSecondAddress() throws Exception {
        MvcResult result = mockMvc.perform(post("/customers/createWithAddress")
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
                                "    }, " +
                                "    \"address\":{\n" +
                                "        \"houseNumber\": \"43\",\n" +
                                "        \"street\": \"Rolig\",\n" +
                                "        \"city\": \"Oslo\",\n" +
                                "        \"postalCode\": \"3121\",\n" +
                                "        \"country\": \"Norway\"\n" +
                                "    }\n" +
                                "}"))
                .andExpect(status().isOk())
                .andDo(result1 -> {
                    System.out.println(result1.getResponse().getContentAsString());
                })
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long customerId = jsonNode.get("customerId").asLong();

        mockMvc.perform(get("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John"))
                .andReturn();

        MvcResult result2 = mockMvc.perform(get("/customers/{id}/addresses", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Shanelland"))
                .andReturn();

        System.out.println(result2.getResponse().getContentAsString());
    }
}
