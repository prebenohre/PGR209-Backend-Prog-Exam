package no.kristiania.ordersystemformachinefactory.model;

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

public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "machine_seq_gen")
    @SequenceGenerator(name = "machine_seq_gen", sequenceName = "machine_seq", allocationSize = 1)
    @Column(name = "machine_id")
    private Long machineId = 0L;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "manufacturer")
    private String manufacturer;


    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "machine_id")
    private Set<Subassembly> subassemblies = new HashSet<>();

    public Machine(String modelName, String manufacturer) {
        this.modelName = modelName;
        this.manufacturer = manufacturer;
    }
}
