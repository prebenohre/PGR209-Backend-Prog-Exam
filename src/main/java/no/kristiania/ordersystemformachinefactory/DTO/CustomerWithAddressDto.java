package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.repository.CustomerRepository;

@Getter
@Setter
@NoArgsConstructor
public class CustomerWithAddressDto {
    private Customer customer;
    private Address address;

    public CustomerWithAddressDto(Customer customer, Address address){
        this.address = address;
        this.customer = customer;
    }
}
