package no.kristiania.ordersystemformachinefactory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity

public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_seq_gen")
    @SequenceGenerator(name = "part_seq_gen", sequenceName = "part_seq", allocationSize = 1)
    @Column(name = "part_id")
    private Long partId = 0L;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "specifications")
    private String specifications;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subassembly_id")
    private Subassembly subassembly;

    public Part(String partName, String manufacturer, String specifications) {
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.specifications = specifications;
    }
}
