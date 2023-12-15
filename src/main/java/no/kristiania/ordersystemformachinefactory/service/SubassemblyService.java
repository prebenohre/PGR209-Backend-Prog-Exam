package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.DTO.AddPartToSubassemblyDto;
import no.kristiania.ordersystemformachinefactory.model.Part;
import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import no.kristiania.ordersystemformachinefactory.repository.PartRepository;
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
    private final PartRepository partRepository;

    @Autowired
    public SubassemblyService(SubassemblyRepository subassemblyRepository, PartRepository partRepository) {
        this.subassemblyRepository = subassemblyRepository;
        this.partRepository = partRepository;
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
                    subassembly.setParts(updatedSubassembly.getParts());
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

    public Subassembly addPartToSubassembly(Long subassemblyId, AddPartToSubassemblyDto partDto) {
        Subassembly subassembly = subassemblyRepository.findById(subassemblyId)
                .orElseThrow(() -> new RuntimeException("Subassembly not found"));

        Part newPart = new Part(partDto.getPartName(), partDto.getManufacturer(), partDto.getSpecifications());
        newPart.setSubassembly(subassembly);
        partRepository.save(newPart);

        return subassembly;
    }
}
