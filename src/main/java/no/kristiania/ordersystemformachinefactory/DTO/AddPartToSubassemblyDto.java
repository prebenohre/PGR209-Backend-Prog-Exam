package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddPartToSubassemblyDto {
    private String partName;
    private String manufacturer;
    private String specifications;
}
