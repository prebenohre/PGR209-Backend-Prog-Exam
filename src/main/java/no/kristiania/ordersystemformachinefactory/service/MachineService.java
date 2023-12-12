package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Customer;
import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public List<Machine> findAllMachines() {
        return machineRepository.findAll();
    }

    public Optional<Machine> findMachineById(Long id) {
        return machineRepository.findById(id);
    }

    public Machine saveMachine(Machine machine) {
        return machineRepository.save(machine);
    }

    public void deleteMachine(Long id) {
        machineRepository.deleteById(id);
    }

    public Machine updateMachine(Long id, Machine updatedMachine) {
        return machineRepository.findById(id)
                .map(machine -> {
                    machine.setModelName(updatedMachine.getModelName());
                    machine.setManufacturer(updatedMachine.getManufacturer());
                    machine.setSubassemblies(updatedMachine.getSubassemblies()); // Pass på at dette håndterer relasjoner korrekt
                    return machineRepository.save(machine);
                })
                .orElseGet(() -> {
                    updatedMachine.setMachineId(id);
                    return machineRepository.save(updatedMachine);
                });
    }

    public List<Machine> getMachinesPageable(int pageNumber){
        return machineRepository.findAll(PageRequest.of(pageNumber, 10)).stream().toList();
    }
}
