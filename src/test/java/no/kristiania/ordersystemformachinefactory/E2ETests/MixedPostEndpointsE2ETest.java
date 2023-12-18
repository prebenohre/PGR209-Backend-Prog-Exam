package no.kristiania.ordersystemformachinefactory.E2ETests;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.DTO.AddOrderToCustomerDto;
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
public class MixedPostEndpointsE2ETest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void subassemblyToMachineToOrderToCustomer() throws Exception {
        //Test setup
        Order customerOrder = new Order(new Date());
        Customer customerThatOrders = new Customer("Peter Orders", "orders@yahoo.com");
        Subassembly subassembly = new Subassembly("Hydraulic Arm");
        Machine machine = new Machine("CoolCool Machine", "Machine LLC.");
        Part part = new Part("Cog", "Cogmakers", "Huge Cog");


        //01. Customer post
        ResultActions postCustomer = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerThatOrders)))
                .andExpect(status().isOk());

        //02. Order post
        ResultActions postOrder = mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerOrder)))
                .andExpect(status().isOk());

        //03. Machine post
        ResultActions postMachine = mockMvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machine)))
                .andExpect(status().isOk());

        //04. Subassembly post
        ResultActions postSubassembly = mockMvc.perform(post("/subassemblies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subassembly)))
                .andExpect(status().isOk());

        //05. Part post
        ResultActions postPart = mockMvc.perform(post("/parts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(part)))
                .andExpect(status().isOk());

        //06. Extract customer ID from postCustomer
        Customer createdCustomer = objectMapper.readValue(
                postCustomer.andReturn().getResponse().getContentAsString(), Customer.class
        );

        //07. Check that it posted the correct customer
        mockMvc.perform(get("/customers/{id}", createdCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Peter Orders"));

        //08. Add order to customer DTO
        AddOrderToCustomerDto addOrderToCustomerDto = new AddOrderToCustomerDto(new Date(), createdCustomer.getCustomerId());

        //09. Create order for customer. This means the customer now has an order placed on him
        ResultActions orderCreatedForCustomer = mockMvc.perform(post("/orders/createForCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addOrderToCustomerDto)))
                .andExpect(status().isOk());

        //10. Add part to subassembly
        Subassembly createdSubassembly = objectMapper.readValue(postSubassembly.andReturn().getResponse().getContentAsString(), Subassembly.class);

        mockMvc.perform(post("/subassemblies/{id}/addPart", createdSubassembly.getSubassemblyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(part))).andExpect(status().isOk());

        Machine createdMachine = objectMapper.readValue(postMachine.andReturn().getResponse().getContentAsString(), Machine.class);

        mockMvc.perform(post("/machines/{id}/addSubassembly", createdMachine.getMachineId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdSubassembly)))
                .andExpect(status().isOk());

        //11. Add the machine to the order
        Order orderToAddMachine = objectMapper.readValue(orderCreatedForCustomer.andReturn().getResponse().getContentAsString(), Order.class);

        mockMvc.perform(post("/orders/{id}/addMachine", orderToAddMachine.getOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdMachine)))
                .andExpect(status().isOk());
    }
}

