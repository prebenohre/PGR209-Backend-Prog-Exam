package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.model.Part;
import no.kristiania.ordersystemformachinefactory.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {

    private final PartRepository partRepository;

    @Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public List<Part> findAllParts() {
        return partRepository.findAll();
    }

    public Optional<Part> findPartById(Long id) {
        return partRepository.findById(id);
    }

    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }

    public Part updatePart(Long id, Part updatedPart) {
        return partRepository.findById(id)
                .map(part -> {
                    part.setPartName(updatedPart.getPartName());
                    part.setManufacturer(updatedPart.getManufacturer());
                    part.setSpecifications(updatedPart.getSpecifications());
                    return partRepository.save(part);
                })
                .orElseGet(() -> {
                    updatedPart.setPartId(id);
                    return partRepository.save(updatedPart);
                });
    }

    public List<Part> getPartsPageable(int pageNumber){
        return partRepository.findAll(PageRequest.of(pageNumber, 10)).stream().toList();
    }
}
