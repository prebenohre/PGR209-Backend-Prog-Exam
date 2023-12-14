package no.kristiania.ordersystemformachinefactory.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class AllBaseGetEndpointsTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getCustomersWorks() throws Exception{
        mockMvc.perform(get("/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAddressesWorks() throws Exception{
        mockMvc.perform(get("/addresses"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMachinesWorks() throws Exception{
        mockMvc.perform(get("/machines"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getOrdersWorks() throws Exception{
        mockMvc.perform(get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getPartsWorks() throws Exception{
        mockMvc.perform(get("/parts"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getSubassembliesWorks() throws Exception{
        mockMvc.perform(get("/subassemblies"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
