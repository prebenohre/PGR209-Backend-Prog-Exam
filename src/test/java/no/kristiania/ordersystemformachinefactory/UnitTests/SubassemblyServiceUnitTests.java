package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Subassembly;
import no.kristiania.ordersystemformachinefactory.repository.SubassemblyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class SubassemblyServiceUnitTests {

    @MockBean
    SubassemblyRepository subassemblyRepository;

    @Test
    void shouldAddSubassemblies(){
        Subassembly subassembly1 = new Subassembly("Best");
        Subassembly subassembly2 = new Subassembly("Top Notch");
        Subassembly subassembly3 = new Subassembly("Cool");
        Subassembly subassembly4 = new Subassembly("Awesome");

        List<Subassembly> subassemblyList = List.of(subassembly1,subassembly2,subassembly3,subassembly4);

        when(subassemblyRepository.findAll()).thenReturn(subassemblyList);

        var subassemblies = subassemblyRepository.findAll();

        assert subassemblies.get(2).getName().equals("Cool");
        assert subassemblies.size() == 4;
        assert subassemblies.size() != 5;
        System.out.println(subassemblies.size());
    }
}
