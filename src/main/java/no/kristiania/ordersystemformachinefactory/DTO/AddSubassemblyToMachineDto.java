package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no.kristiania.ordersystemformachinefactory.model.Subassembly;

@Getter
@Setter
@NoArgsConstructor
public class AddSubassemblyToMachineDto {
    private Long machineId;
    private Subassembly subassembly;
}
