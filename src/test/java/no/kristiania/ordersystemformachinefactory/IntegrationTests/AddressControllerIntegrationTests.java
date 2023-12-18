package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllAddresses_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/addresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createAddress_ShouldReturnCreatedAddress() throws Exception {
        Address newAddress = new Address("123", "Test Street", "Test City", "12345", "Test Country");
        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street", is(newAddress.getStreet())));
    }

    @Test
    public void getAddressById_ShouldReturnAddress() throws Exception {
        long addressId = 1L;
        mockMvc.perform(get("/addresses/" + addressId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId", is((int) addressId)));
    }

    @Test
    public void updateAddress_ShouldReturnUpdatedAddress() throws Exception {
        long addressId = 1L;
        Address updatedAddress = new Address("123", "Updated Street", "Updated City", "12345", "Updated Country");
        mockMvc.perform(put("/addresses/" + addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAddress)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street", is(updatedAddress.getStreet())));
    }

    @Test
    public void deleteAddress_ShouldReturnOk() throws Exception {
        long addressId = 1L; // Anta at denne ID-en finnes i databasen
        mockMvc.perform(delete("/addresses/" + addressId))
                .andExpect(status().isOk());
    }
}

