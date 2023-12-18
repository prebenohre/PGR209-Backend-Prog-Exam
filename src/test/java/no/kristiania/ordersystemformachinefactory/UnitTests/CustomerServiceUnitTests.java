package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.AddressRepository;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import no.kristiania.ordersystemformachinefactory.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceUnitTests {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldGetCustomers() {
        List<Customer> customerList = List.of(new Customer("Cus1", "Cus1@Gmail.com"), new Customer("Cus2", "Cus2@Gmail.com"));
        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> customers = customerService.findAllCustomers();

        assert customers.size() == 2;
        assert "Cus1".equals(customers.get(0).getCustomerName());
        assert "Cus2".equals(customers.get(1).getCustomerName());
    }

    @Test
    void shouldSaveCustomer() {
        Customer newCustomer = new Customer("New Customer", "new@customer.com");
        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        Customer savedCustomer = customerService.saveCustomer(newCustomer);

        assert "New Customer".equals(savedCustomer.getCustomerName());
    }

    @Test
    void shouldUpdateCustomer() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer("Old Name", "old@customer.com");
        existingCustomer.setCustomerId(customerId);
        Customer updatedCustomer = new Customer("Updated Name", "updated@customer.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);

        Customer result = customerService.updateCustomer(customerId, updatedCustomer);

        assert "Updated Name".equals(result.getCustomerName());
    }

    @Test
    void shouldDeleteCustomer() {
        Long customerId = 1L;
        doNothing().when(customerRepository).deleteById(customerId);

        customerService.deleteCustomer(customerId);

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    void shouldFindCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer("Find Me", "findme@customer.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.findCustomerById(customerId);

        assert foundCustomer.isPresent();
        assert "Find Me".equals(foundCustomer.get().getCustomerName());
    }

    @Test
    void shouldCreateCustomerWithAddress() {
        Customer customer = new Customer("Test Customer", "test@customer.com");
        Address address = new Address("1", "Test Street", "Test City", "1234", "Test Country");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Customer savedCustomer = customerService.createCustomerWithAddress(customer, address);

        assert savedCustomer.getAddresses().contains(address);
    }

    @Test
    void shouldAddAddressToExistingCustomer() {
        Long customerId = 1L;
        Customer existingCustomer = new Customer("Existing Customer", "existing@customer.com");
        existingCustomer.setCustomerId(customerId);
        Address newAddress = new Address("2", "New Street", "New City", "5678", "New Country");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

        existingCustomer.getAddresses().add(newAddress);

        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        Customer updatedCustomer = customerService.addAddressToCustomer(customerId, newAddress);

        assert updatedCustomer != null;
        assert updatedCustomer.getAddresses().contains(newAddress);
    }
}
