package no.kristiania.ordersystemformachinefactory;

import com.github.javafaker.Faker;
import no.kristiania.ordersystemformachinefactory.model.*;
import no.kristiania.ordersystemformachinefactory.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.*;

@Configuration
public class DataLoader {

    @Bean
    @Profile("dev")
    CommandLineRunner initDatabase(AddressRepository addressRepository,
                                   CustomerRepository customerRepository,
                                   MachineRepository machineRepository,
                                   OrderRepository orderRepository,
                                   PartRepository partRepository,
                                   SubassemblyRepository subassemblyRepository) {
        return args -> {
            Faker faker = new Faker();

            // Generer og lagre adresser
            Set<Address> addresses = new HashSet<>();
            for (int i = 0; i < 50; i++) {
                Address address = new Address(
                        faker.address().buildingNumber(),
                        faker.address().streetName(),
                        faker.address().city(),
                        faker.address().zipCode(),
                        faker.address().country());
                addresses.add(addressRepository.save(address));
            }

            // Generer og lagre kunder med matchende navn og e-postadresser
            List<Address> addressList = new ArrayList<>(addresses);
            for (int i = 0; i < 50; i++) {
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@example.com";

                Customer customer = new Customer(firstName + " " + lastName, email);

                Address randomAddress = addressList.get(faker.random().nextInt(addressList.size()));
                Set<Address> customerAddresses = new HashSet<>();
                customerAddresses.add(randomAddress);
                customer.setAddresses(customerAddresses);

                customerRepository.save(customer);
            }

            // Generer og lagre maskiner, undermonteringer og deler
            for (int i = 0; i < 50; i++) {
                Machine machine = new Machine(
                        faker.commerce().productName(),
                        faker.company().name());
                machine = machineRepository.save(machine);

                for (int j = 0; j < 3; j++) {
                    String subassemblyName = faker.commerce().material() + " " + faker.lorem().word();
                    Subassembly subassembly = new Subassembly(subassemblyName);
                    subassembly.setMachine(machine);
                    subassembly = subassemblyRepository.save(subassembly);

                    for (int k = 0; k < 5; k++) {
                        Part part = new Part(
                                faker.commerce().productName(),
                                faker.company().name(),
                                faker.lorem().sentence());
                        part.setSubassembly(subassembly);
                        partRepository.save(part);
                    }
                }
            }

            // Generer og lagre ordre med maskiner
            List<Customer> customers = customerRepository.findAll();
            List<Machine> machines = machineRepository.findAll();
            for (int i = 0; i < customers.size(); i++) {
                Order order = new Order(new Date());
                order.setCustomer(customers.get(i % customers.size()));

                // Velg et tilfeldig sett med maskiner og tilordne dem til ordren
                Set<Machine> orderMachines = new HashSet<>();
                for (int j = 0; j < faker.random().nextInt(1, machines.size()); j++) {
                    orderMachines.add(machines.get(faker.random().nextInt(machines.size())));
                }
                order.setMachines(orderMachines);

                orderRepository.save(order);
            }
        };
    }
}
