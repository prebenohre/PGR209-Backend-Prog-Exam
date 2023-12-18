package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.repository.MachineRepository;
import no.kristiania.ordersystemformachinefactory.service.MachineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MachineServiceUnitTest {

    @Autowired
    private MachineService machineService;

    @MockBean
    private MachineRepository machineRepository;

    @Test
    void shouldFindAllMachines() {
        List<Machine> machines = List.of(new Machine("Model1", "Manufacturer1"), new Machine("Model2", "Manufacturer2"));
        when(machineRepository.findAll()).thenReturn(machines);

        List<Machine> found = machineService.findAllMachines();

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void shouldFindMachineById() {
        Machine machine = new Machine("Model1", "Manufacturer1");
        when(machineRepository.findById(1L)).thenReturn(Optional.of(machine));

        Optional<Machine> found = machineService.findMachineById(1L);

        assertTrue(found.isPresent());
        assertEquals("Model1", found.get().getModelName());
    }

    @Test
    void shouldSaveMachine() {
        Machine machine = new Machine("Model1", "Manufacturer1");
        when(machineRepository.save(any(Machine.class))).thenReturn(machine);

        Machine saved = machineService.saveMachine(machine);

        assertNotNull(saved);
        assertEquals("Model1", saved.getModelName());
    }

    // Lignende tester for updateMachine og deleteMachine
}
