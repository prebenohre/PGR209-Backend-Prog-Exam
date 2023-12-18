package no.kristiania.ordersystemformachinefactory.UnitTests;

import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.OrderRepository;
import no.kristiania.ordersystemformachinefactory.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DataJpaTest
public class OrderServiceUnitTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateAnOrderAndAssertDate() {
        Date date = new Date();
        List<Order> orderList = List.of(new Order(date));
        when(orderRepository.findAll()).thenReturn(orderList);

        var orders = orderService.findAllOrders();

        assert orders.size() == 1;
        assert orders.get(0).getOrderDate().equals(date);
    }

    @Test
    void shouldSaveOrder() {
        Date date = new Date();
        Order order = new Order(date);
        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveOrder(order);

        assert savedOrder.getOrderDate().equals(date);
    }

    @Test
    void shouldUpdateOrder() {
        Long orderId = 1L;
        Order existingOrder = new Order(new Date());
        existingOrder.setOrderId(orderId);
        Date newDate = new Date();
        Order updatedOrder = new Order(newDate);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(orderId, updatedOrder);

        assert result.getOrderDate().equals(newDate);
    }

    @Test
    void shouldDeleteOrder() {
        Long orderId = 1L;
        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void shouldFindOrderById() {
        Long orderId = 1L;
        Order order = new Order(new Date());
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        var foundOrder = orderService.findOrderById(orderId);

        assert foundOrder.isPresent();
    }
}

