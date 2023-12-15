package no.kristiania.ordersystemformachinefactory.service;

import no.kristiania.ordersystemformachinefactory.model.Machine;
import no.kristiania.ordersystemformachinefactory.model.Order;
import no.kristiania.ordersystemformachinefactory.repository.MachineRepository;
import no.kristiania.ordersystemformachinefactory.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MachineRepository machineRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MachineRepository machineRepository) {
        this.orderRepository = orderRepository;
        this.machineRepository = machineRepository;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(updatedOrder.getOrderDate());
                    order.setCustomer(updatedOrder.getCustomer());
                    order.setMachines(updatedOrder.getMachines());
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    updatedOrder.setOrderId(id);
                    return orderRepository.save(updatedOrder);
                });
    }

    public Page<Order> getOrdersPageable(int pageNumber, int pageSize){
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Order addMachineToOrder(Long orderId, Long machineId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new RuntimeException("Machine not found"));

        order.getMachines().add(machine);
        return orderRepository.save(order);
    }
}
