package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Part;
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
public class PartControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllParts_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/parts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createPart_ShouldReturnCreatedPart() throws Exception {
        Part newPart = new Part("PartName", "Manufacturer", "Specifications");
        mockMvc.perform(post("/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partName", is(newPart.getPartName())));
    }

    @Test
    public void getPartById_ShouldReturnPart() throws Exception {
        long partId = 1L;
        mockMvc.perform(get("/parts/" + partId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partId", is((int) partId)));
    }

    @Test
    public void updatePart_ShouldReturnUpdatedPart() throws Exception {
        long partId = 1L;
        Part updatedPart = new Part("UpdatedName", "UpdatedManufacturer", "UpdatedSpecifications");
        mockMvc.perform(put("/parts/" + partId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPart)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.partName", is(updatedPart.getPartName())));
    }

    @Test
    public void deletePart_ShouldReturnOk() throws Exception {
        long partId = 1L;
        mockMvc.perform(delete("/parts/" + partId))
                .andExpect(status().isOk());
    }
}

