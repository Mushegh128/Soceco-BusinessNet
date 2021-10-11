package am.hovall.common.services.impl;

import am.hovall.common.model.OrderStatus;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;
import am.hovall.common.repositories.OrderRepository;
import am.hovall.common.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Double getCompanyDebt(Company company) {
        return null;
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public List<Order> findAllByCompany(Optional<Company> company) {
        return null;
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }

    @Override
    public boolean changeOrderStatus(OrderStatus orderStatus) {
        return false;
    }
}
