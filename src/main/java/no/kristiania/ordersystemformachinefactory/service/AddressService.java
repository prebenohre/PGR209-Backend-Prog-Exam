package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    public Optional<Address> findAddressById(Long id) {
        return addressRepository.findById(id);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public Address updateAddress(Long id, Address updatedAddress) {
        return addressRepository.findById(id)
                .map(address -> {
                    address.setHouseNumber(updatedAddress.getHouseNumber());
                    address.setStreet(updatedAddress.getStreet());
                    address.setCity(updatedAddress.getCity());
                    address.setPostalCode(updatedAddress.getPostalCode());
                    address.setCountry(updatedAddress.getCountry());
                    return addressRepository.save(address);
                })
                .orElseGet(() -> {
                    updatedAddress.setAddressId(id);
                    return addressRepository.save(updatedAddress);
                });
    }

    public Page<Address> getAddressesPageable(int pageNumber, int pageSize){
        return addressRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}