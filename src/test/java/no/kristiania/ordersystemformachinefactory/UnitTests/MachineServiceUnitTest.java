package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.repository.MachineRepository;
import no.kristiania.ordersystemformachinefactory.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class MachineServiceUnitTest {
    @MockBean
    private MachineRepository machineRepository;

    @Autowired
    private MachineService machineService;


    @Test
    void machineServiceShouldAddAndRetrieveMachines(){
        List<Machine> machineList = List.of(new Machine("Big Boy", "Steel Inc."), new Machine("Machine2", "Big Machines LLC."));
        when(machineRepository.findAll()).thenReturn(machineList);

        var machines = machineRepository.findAll();

        assert machines.size() == 2;

        assert machines.get(0).getModelName() == "Big Boy";
    }

    @Test
    void shouldGetItemsFromRepo(){
        ArrayList<Machine> machineArrayList = new ArrayList<>(List.of(new Machine("Cool Machine", "Machinemaker")));

        when(machineRepository.findAll()).thenReturn(machineArrayList);

        assert machineService.findAllMachines().get(0).getModelName().equals("Cool Machine");
    }
}
