package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.Subassembly;
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
public class SubassemblyControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllSubassemblies_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/subassemblies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))));
    }

    @Test
    public void createSubassembly_ShouldReturnCreatedSubassembly() throws Exception {
        Subassembly newSubassembly = new Subassembly("NewSubassembly");
        mockMvc.perform(post("/subassemblies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSubassembly)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(newSubassembly.getName())));
    }

    @Test
    public void getSubassemblyById_ShouldReturnSubassembly() throws Exception {
        long subassemblyId = 1L;
        mockMvc.perform(get("/subassemblies/" + subassemblyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subassemblyId", is((int) subassemblyId)));
    }

    @Test
    public void updateSubassembly_ShouldReturnUpdatedSubassembly() throws Exception {
        long subassemblyId = 1L;
        Subassembly updatedSubassembly = new Subassembly("UpdatedSubassembly");
        mockMvc.perform(put("/subassemblies/" + subassemblyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSubassembly)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedSubassembly.getName())));
    }

    @Test
    public void deleteSubassembly_ShouldReturnOk() throws Exception {
        long subassemblyId = 1L; // Anta at denne ID-en finnes i databasen
        mockMvc.perform(delete("/subassemblies/" + subassemblyId))
                .andExpect(status().isOk());
    }
}

