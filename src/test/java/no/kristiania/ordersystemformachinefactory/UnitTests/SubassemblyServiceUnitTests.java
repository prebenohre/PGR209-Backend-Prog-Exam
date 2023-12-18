package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import no.kristiania.ordersystemformachinefactory.repository.SubassemblyRepository;
import no.kristiania.ordersystemformachinefactory.service.SubassemblyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SubassemblyServiceUnitTests {

    @Autowired
    private SubassemblyService subassemblyService;

    @MockBean
    private SubassemblyRepository subassemblyRepository;

    @Test
    void shouldFindAllSubassemblies() {
        Subassembly subassembly1 = new Subassembly("Subassembly 1");
        Subassembly subassembly2 = new Subassembly("Subassembly 2");
        List<Subassembly> subassemblies = List.of(subassembly1, subassembly2);
        when(subassemblyRepository.findAll()).thenReturn(subassemblies);

        List<Subassembly> found = subassemblyService.findAllSubassemblies();

        assertNotNull(found);
        assertEquals(2, found.size());
    }

    @Test
    void shouldFindSubassemblyById() {
        Subassembly subassembly = new Subassembly("Subassembly 1");
        when(subassemblyRepository.findById(1L)).thenReturn(Optional.of(subassembly));

        Optional<Subassembly> found = subassemblyService.findSubassemblyById(1L);

        assertTrue(found.isPresent());
        assertEquals("Subassembly 1", found.get().getName());
    }

    @Test
    void shouldSaveSubassembly() {
        Subassembly subassembly = new Subassembly("Subassembly 1");
        when(subassemblyRepository.save(any(Subassembly.class))).thenReturn(subassembly);

        Subassembly saved = subassemblyService.saveSubassembly(subassembly);

        assertNotNull(saved);
        assertEquals("Subassembly 1", saved.getName());
    }
}
