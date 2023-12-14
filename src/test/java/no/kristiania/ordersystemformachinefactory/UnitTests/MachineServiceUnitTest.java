package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.repository.MachineRepository;
import no.kristiania.ordersystemformachinefactory.service.MachineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class MachineServiceUnitTest {
    @MockBean
    private MachineRepository machineRepository;


    @Test
    void machineServiceShouldAddAndRetrieveMachines(){
        List<Machine> machineList = List.of(new Machine("Big Boy", "Steel Inc."), new Machine("Machine2", "Big Machines LLC."));
        when(machineRepository.findAll()).thenReturn(machineList);

        var machines = machineRepository.findAll();

        assert machines.size() == 2;

        assert machines.get(0).getModelName() == "Big Boy";
    }
}
