package no.kristiania.ordersystemformachinefactory.controller;

import no.kristiania.ordersystemformachinefactory.DTO.AddAddressToCustomerDto;
import no.kristiania.ordersystemformachinefactory.DTO.CustomerWithAddressDto;
import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<Customer>> getCustomersByPage(
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int size) {
        Page<Customer> page = customerService.getCustomersPageable(pageNumber, size);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/createWithAddress")
    public Customer createCustomerWithAddress(@RequestBody CustomerWithAddressDto dto) {
        return customerService.createCustomerWithAddress(dto.getCustomer(), dto.getAddress());
    }

    @PostMapping("/addNewAddress")
    public ResponseEntity<Customer> addNewAddressToCustomer(@RequestBody AddAddressToCustomerDto dto) {
        Customer updatedCustomer = customerService.addAddressToCustomer(dto.getCustomerId(), dto.getAddress());
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<Set<Address>> getCustomerAddress(@PathVariable Long id){
        try{
            Set<Address> customerAddresses = customerService.getCustomerAddresses(id);
            return ResponseEntity.ok(customerAddresses);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
