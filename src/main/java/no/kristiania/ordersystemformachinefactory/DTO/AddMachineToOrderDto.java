package no.kristiania.ordersystemformachinefactory.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddMachineToOrderDto {
    private Long orderId;
    private Long machineId;
}
