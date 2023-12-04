package no.kristiania.ordersystemformachinefactory.repository;

import no.kristiania.ordersystemformachinefactory.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Her kan vi legge til egendefinerte metoder etter behov
}
