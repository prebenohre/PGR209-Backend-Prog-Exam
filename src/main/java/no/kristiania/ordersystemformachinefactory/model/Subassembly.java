package no.kristiania.ordersystemformachinefactory.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class Subassembly {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subassembly_seq_gen")
    @SequenceGenerator(name = "subassembly_seq_gen", sequenceName = "subassembly_seq", allocationSize = 1)
    @Column(name = "subassembly_id")
    private Long subassemblyId = 0L;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "subassembly_id")
    private Set<Part> parts = new HashSet<>();

    public Subassembly(String name) {
        this.name = name;
    }

}
