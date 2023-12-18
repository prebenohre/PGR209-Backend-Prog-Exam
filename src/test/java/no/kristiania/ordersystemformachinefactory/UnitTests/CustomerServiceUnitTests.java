package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceUnitTests {
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void customerRepository_shouldGet2Customers_ToBeTrue() {
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert customers.size() == 2;
    }

    @Test
    void customerRepository_shouldGet3Customers_ToBeFalse() {
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert customers.size() != 3;
    }

    @Test
    void customerRepository_shouldGetFirsCustomerName_true() {
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert Objects.equals(customers.get(0).getCustomerName(), "Cus1");
    }

    @Test
    void testAddAddressToCustomer() {
        //Test setup
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@Gmail.com"));
        Set<Address> customer2Addresses = new HashSet<>();
        customer2Addresses.add(new Address("21", "Gate", "Tønsberg", "3121", "Norge"));
        customer2Addresses.add(new Address("21", "Street", "Oslo", "0812", "Norge"));
        customerList.get(0).setAddresses(customer2Addresses);

        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert Objects.equals(customers.get(0).getAddresses().size(), 2);
    }

    @Test
    void ShouldAddOrderToCustomer() {
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@Gmail.com"));

        Set<Order> orders = new HashSet<>();
        orders.add(new Order(new Date()));

        customerList.get(0).setOrders(orders);

        when(customerRepository.findAll()).thenReturn(customerList);
        var customers = customerRepository.findAll();

        assert Objects.equals(customers.get(0).getOrders().size(), 1);
    }
}