package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import no.kristiania.ordersystemformachinefactory.repository.SubassemblyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubassemblyService {

    private final SubassemblyRepository subassemblyRepository;

    @Autowired
    public SubassemblyService(SubassemblyRepository subassemblyRepository) {
        this.subassemblyRepository = subassemblyRepository;
    }

    public List<Subassembly> findAllSubassemblies() {
        return subassemblyRepository.findAll();
    }

    public Optional<Subassembly> findSubassemblyById(Long id) {
        return subassemblyRepository.findById(id);
    }

    public Subassembly saveSubassembly(Subassembly subassembly) {
        return subassemblyRepository.save(subassembly);
    }

    public void deleteSubassembly(Long id) {
        subassemblyRepository.deleteById(id);
    }

    public Subassembly updateSubassembly(Long id, Subassembly updatedSubassembly) {
        return subassemblyRepository.findById(id)
                .map(subassembly -> {
                    subassembly.setName(updatedSubassembly.getName());
                    subassembly.setMachine(updatedSubassembly.getMachine());
                    subassembly.setParts(updatedSubassembly.getParts()); // Pass på at dette håndterer relasjonene korrekt
                    return subassemblyRepository.save(subassembly);
                })
                .orElseGet(() -> {
                    updatedSubassembly.setSubassemblyId(id);
                    return subassemblyRepository.save(updatedSubassembly);
                });
    }

    public Page<Subassembly> getSubassembliesPageable(int pageNumber, int pageSize){
        return subassemblyRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
