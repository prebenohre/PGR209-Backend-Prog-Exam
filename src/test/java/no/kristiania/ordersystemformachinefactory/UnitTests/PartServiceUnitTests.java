package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Part;
import no.kristiania.ordersystemformachinefactory.repository.PartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class PartServiceUnitTests {

    @MockBean
    PartRepository partRepository;

    @Test
    void shouldAddParts(){
        Part part1 = new Part("Cog", "Cogmakers INC.", "Nice part to have!");
        Part part2 = new Part("Screw", "ScrewKings", "Screws made of non-recyclable material!");
        List<Part> partList = List.of(part1, part2);
        when(partRepository.findAll()).thenReturn(partList);

        var parts = partRepository.findAll();

        assert parts.size() == 2;
        assert parts.get(0).getPartName().equals("Cog");
        assert parts.get(1).getManufacturer().equals("ScrewKings");
    }
}
