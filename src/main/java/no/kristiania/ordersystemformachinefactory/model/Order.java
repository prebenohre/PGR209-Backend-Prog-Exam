package no.kristiania.ordersystemformachinefactory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders") // "order" er et reservert ord i mange SQL-dialekter, derfor må vi bruke "orders"

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_gen")
    @SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId = 0L;

    @Column(name = "order_date")
    private Date orderDate;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Endre fra @OneToMany til @ManyToMany
    @ManyToMany
    @JoinTable(
            name = "order_machine",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private Set<Machine> machines = new HashSet<>();

    public Order(Date orderDate) {
        this.orderDate = orderDate;
    }
}

