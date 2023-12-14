package no.kristiania.ordersystemformachinefactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class EndToEndTestsDeprecated {

    @Autowired
    MockMvc mockMvc;

    /*{
    "customer":{
        "customerEmail": "John@John.com",
        "customerName": "John"
    },
    "address":{
                "houseNumber": "91856",
        "street": "Darin Ridge",
        "city": "Shanelland",
        "postalCode": "83469",
        "country": "Yemen"
    }
}*/

    private CustomerRepository customerRepository;
    @Autowired
    ObjectMapper objectMapper;
    /*@Test
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
    }*/


    //Dette er egentlig en integration test


    //Jobber videre med den senere
    /*@Test
    void testAddNewAddressToCustomer() throws Exception {
        // Create a customer with an initial address
        Customer newCustomer = new Customer();
        Address initialAddress = new Address("104", "Gate", "Tønsberg", "3031", "Norway");
        Set<Address> customerAddresses = new HashSet<>();
        customerAddresses.add(initialAddress);
        newCustomer.setCustomerName("John Doe");
        newCustomer.setCustomerEmail("john.doe@example.com");
        newCustomer.setAddresses(customerAddresses);



        // Perform a POST request to create the customer with an initial address
        ResultActions createResult = mockMvc.perform(post("/customers/createWithAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CustomerWithAddressDto(newCustomer, initialAddress))))
                .andExpect(status().isOk());

        // Extract the created customer from the response JSON
        Customer createdCustomer = objectMapper.readValue(
                createResult.andReturn().getResponse().getContentAsString(),
                Customer.class);

        // Create a new address
        Address newAddress = new Address("105", "New Street", "Oslo", "1234", "Norway");

        // Perform a POST request to add a new address to the existing customer
        mockMvc.perform(post("/customers/addNewAddress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddAddressToCustomerDto(createdCustomer.getCustomerId(), newAddress))))
                .andExpect(status().isOk());

        // Perform a GET request to retrieve the updated customer and check the new address
        mockMvc.perform(get("/addresses/1", createdCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.street").value("Gate")); // Initial address
    }*/
}
