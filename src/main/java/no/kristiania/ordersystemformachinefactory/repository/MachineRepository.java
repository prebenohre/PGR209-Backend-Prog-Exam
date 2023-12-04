package no.kristiania.ordersystemformachinefactory.repository;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    // Her kan du legge til eventuelle egendefinerte metoder
}
