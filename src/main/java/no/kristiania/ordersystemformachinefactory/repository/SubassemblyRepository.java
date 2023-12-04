package no.kristiania.ordersystemformachinefactory.repository;

import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubassemblyRepository extends JpaRepository<Subassembly, Long> {
    // Her kan du legge til eventuelle egendefinerte metoder
}
