package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Part;
import no.kristiania.ordersystemformachinefactory.repository.PartRepository;
import no.kristiania.ordersystemformachinefactory.service.PartService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DataJpaTest
public class PartServiceUnitTests {

    @Mock
    private PartRepository partRepository;

    @InjectMocks
    private PartService partService;

    @Test
    void shouldAddParts() {
        Part part1 = new Part("Cog", "Cogmakers INC.", "Nice part to have!");
        Part part2 = new Part("Screw", "ScrewKings", "Screws made of non-recyclable material!");
        List<Part> partList = List.of(part1, part2);
        when(partRepository.findAll()).thenReturn(partList);

        var parts = partService.findAllParts();

        assert parts.size() == 2;
        assert parts.get(0).getPartName().equals("Cog");
        assert parts.get(1).getManufacturer().equals("ScrewKings");
    }

    @Test
    void shouldSavePart() {
        Part part = new Part("Bolt", "BoltBuilders", "Strong and reliable");
        when(partRepository.save(part)).thenReturn(part);

        Part savedPart = partService.savePart(part);

        assert savedPart.getPartName().equals("Bolt");
    }

    @Test
    void shouldUpdatePart() {
        Part existingPart = new Part("Old Part", "Old Manufacturer", "Old Description");
        existingPart.setPartId(1L);

        Part updatedPart = new Part("Updated Part", "Updated Manufacturer", "Updated Description");
        when(partRepository.findById(1L)).thenReturn(Optional.of(existingPart));
        when(partRepository.save(existingPart)).thenReturn(updatedPart);

        Part result = partService.updatePart(1L, updatedPart);

        assert result.getPartName().equals("Updated Part");
    }

    @Test
    void shouldDeletePart() {
        Long partId = 1L;
        doNothing().when(partRepository).deleteById(partId);

        partService.deletePart(partId);

        verify(partRepository, times(1)).deleteById(partId);
    }

    @Test
    void shouldFindPartById() {
        Long partId = 1L;
        Part part = new Part("Part", "Manufacturer", "Description");
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));

        var foundPart = partService.findPartById(partId);

        assert foundPart.isPresent();
        assert foundPart.get().getPartName().equals("Part");
    }
}

