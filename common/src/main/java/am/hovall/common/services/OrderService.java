package am.hovall.common.services;

import am.hovall.common.model.OrderStatus;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Double getCompanyDebt(Company company);

    Order save(Order order);

    List<Order> findAllByCompany(Optional<Company> company);

    Order updateOrder(Order order);

    boolean changeOrderStatus(OrderStatus orderStatus);
}
