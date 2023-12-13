package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.AddressRepository;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
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

    public Page<Customer> getCustomersPageable(int pageNumber, int pageSize){
        return customerRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Customer createCustomerWithAddress(Customer customer, Address address) {
        // Lagre adressen først
        Address savedAddress = addressRepository.save(address);

        // Sett adressen til kunden
        Set<Address> addresses = new HashSet<>();
        addresses.add(savedAddress);
        customer.setAddresses(addresses);

        // Lagre og returner kunden
        return customerRepository.save(customer);
    }
}
