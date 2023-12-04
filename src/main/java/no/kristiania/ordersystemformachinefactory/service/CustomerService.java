package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setCustomerName(updatedCustomer.getCustomerName());
                    customer.setCustomerEmail(updatedCustomer.getCustomerEmail());
                    customer.setAddresses(updatedCustomer.getAddresses()); // Anta at dette håndterer relasjoner korrekt
                    return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                    updatedCustomer.setCustomerId(id);
                    return customerRepository.save(updatedCustomer);
                });
    }
}
