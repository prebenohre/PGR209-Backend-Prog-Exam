package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.model.Customer;

@Getter
@Setter
@NoArgsConstructor
public class AddressWithCustomerDto {
    private Address address;
    private Customer customer;
}
