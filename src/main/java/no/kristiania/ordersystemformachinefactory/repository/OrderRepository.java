package no.kristiania.ordersystemformachinefactory.repository;

import no.kristiania.ordersystemformachinefactory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Her kan du legge til eventuelle egendefinerte metoder
}
