package no.kristiania.ordersystemformachinefactory.EndToEndTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MixedPostEndpoints {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void subassemblyToMachineToOrderToCustomer() throws Exception {
        //Test setup

        //Order that should recieve machine
        Order customerOrder = new Order(new Date());
        //The customer that will order
        Customer customerThatOrders = new Customer("Peter Orders", "orders@yahoo.com");
        //Part that will be added to the subassembly
        Subassembly subassembly = new Subassembly("Hydraulic Arm");
        //Machine that the subassembly will be added to
        Machine machine = new Machine("CoolCool Machine", "Machine LLC.");
        //Part that will be added to the machine
        Part part = new Part("Cog", "Cogmakers", "Huge Cog");

        //Make the API call to the specified endpoints for each of the above

        //Customer post
        ResultActions postCustomer = mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerThatOrders)))
                .andExpect(status().isOk());

        //Order post
        ResultActions postOrder = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerOrder)))
                .andExpect(status().isOk());

        //Machine post
        ResultActions postMachine = mockMvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machine)))
                .andExpect(status().isOk());

        //Subassembly post
        ResultActions postSubassembly = mockMvc.perform(post("/subassemblies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subassembly)))
                .andExpect(status().isOk());

        //Part post
        ResultActions postPart = mockMvc.perform(post("/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(part)))
                .andExpect(status().isOk());

        //Extract customer ID from postCustomer
        Customer createdCustomer = objectMapper.readValue(
                postCustomer.andReturn().getResponse().getContentAsString(), Customer.class
        );

        //Check that it posted the correct customer
        mockMvc.perform(get("/customers/{id}", createdCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Peter Orders"));

        //Extract the order ID from postOrder
        Order createdOrder = objectMapper.readValue(
                postOrder.andReturn().getResponse().getContentAsString(), Order.class
        );

        //Check that it posted the correct order
        mockMvc.perform(get("/orders/{id}", createdOrder.getOrderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderDate").exists());



    }
}
