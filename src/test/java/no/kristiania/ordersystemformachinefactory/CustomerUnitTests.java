package no.kristiania.ordersystemformachinefactory;

import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerUnitTests {
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void customerRepository_shouldGet2Customers_ToBeTrue(){
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert customers.size() == 2;
    }

    @Test
    void customerRepository_shouldGet3Customers_ToBeFalse(){
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert customers.size() != 3;
    }

    @Test
    void customerRepository_shouldGetFirsCustomerName_true(){
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@G,ail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        var customers = customerRepository.findAll();
        assert Objects.equals(customers.get(0).getCustomerName(), "Cus1");
    }




}