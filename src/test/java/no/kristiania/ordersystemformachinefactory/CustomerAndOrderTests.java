/*
package no.kristiania.ordersystemformachinefactory;

import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import no.kristiania.ordersystemformachinefactory.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;


@SpringBootTest
public class CustomerAndOrderTests {

    @MockBean
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    @Test
    void shouldPostCustomerAndOrder(){
        List<Customer> customerList =List.of(new Customer("Cus1", "cus1@gmail.com"), new Customer("Cus2", "cus2@gmail.com") );
        List<Order> orderList = List.of(new Order(new Date()));

        orderList.get(0).setCustomer(customerList.get(0));


    }
}
*/
