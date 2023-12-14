package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.kristiania.ordersystemformachinefactory.model.Address;

@Getter
@Setter
@NoArgsConstructor
public class AddAddressToCustomerDto {
    private Long customerId;
    private Address address;

    public AddAddressToCustomerDto(Long customerId, Address address) {
        this.customerId = customerId;
        this.address = address;
    }
}
