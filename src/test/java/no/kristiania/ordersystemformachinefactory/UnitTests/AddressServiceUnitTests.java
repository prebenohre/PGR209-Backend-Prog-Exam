package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Address;
import no.kristiania.ordersystemformachinefactory.repository.AddressRepository;
import no.kristiania.ordersystemformachinefactory.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressServiceUnitTests {
    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    void shouldFindAllAddresses() {
        List<Address> addresses = List.of(
                new Address("123", "Main Street", "Springfield", "12345", "USA"),
                new Address("456", "Elm Street", "Shelbyville", "54321", "USA")
        );
        when(addressRepository.findAll()).thenReturn(addresses);

        List<Address> found = addressService.findAllAddresses();

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void shouldFindAddressById() {
        Address address = new Address("123", "Main Street", "Springfield", "12345", "USA");
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        Optional<Address> found = addressService.findAddressById(1L);

        assertTrue(found.isPresent());
        assertEquals("Main Street", found.get().getStreet());
    }

    @Test
    void shouldSaveAddress() {
        Address address = new Address("123", "Main Street", "Springfield", "12345", "USA");
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        Address saved = addressService.saveAddress(address);

        assertNotNull(saved);
        assertEquals("Main Street", saved.getStreet());
    }
}
