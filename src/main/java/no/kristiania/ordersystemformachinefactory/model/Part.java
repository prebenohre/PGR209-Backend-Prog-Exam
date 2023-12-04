package no.kristiania.ordersystemformachinefactory.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


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

    // Kan inkludere andre felt som pris, dimensjoner, etc. etter behov. Dette kan vi komme tilbake til senere.

    public Part(String partName, String manufacturer, String specifications) {
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.specifications = specifications;
    }
}
