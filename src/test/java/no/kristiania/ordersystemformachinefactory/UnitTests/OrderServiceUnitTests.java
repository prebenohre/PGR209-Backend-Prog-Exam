package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@DataJpaTest
public class OrderServiceUnitTests {

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void shouldCreateAnOrderAndAssertDate(){
        Date date = new Date();
        List<Order> orderList = List.of(new Order(date));
        when(orderRepository.findAll()).thenReturn(orderList);

        var orders = orderRepository.findAll();

        assert orders.size() == 1;
        assert orders.get(0).getOrderDate() == date;
        System.out.println(date);
    }
}
