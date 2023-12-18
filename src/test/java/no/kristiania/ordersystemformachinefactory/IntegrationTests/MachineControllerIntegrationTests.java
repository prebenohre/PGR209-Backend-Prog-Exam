package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Machine;
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
public class MachineControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllMachines_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/machines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createMachine_ShouldReturnCreatedMachine() throws Exception {
        Machine newMachine = new Machine("Test Model", "Test Manufacturer");
        mockMvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMachine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName", is(newMachine.getModelName())));
    }

    @Test
    public void getMachineById_ShouldReturnMachine() throws Exception {
        long machineId = 1L;
        mockMvc.perform(get("/machines/" + machineId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.machineId", is((int) machineId)));
    }

    @Test
    public void updateMachine_ShouldReturnUpdatedMachine() throws Exception {
        long machineId = 1L; // Anta at denne ID-en finnes i databasen
        Machine updatedMachine = new Machine("Updated Model", "Updated Manufacturer");
        mockMvc.perform(put("/machines/" + machineId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMachine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName", is(updatedMachine.getModelName())));
    }
}

