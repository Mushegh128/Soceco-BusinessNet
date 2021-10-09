package am.hovall.common.services;

import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Double getCompanyDebt(Company company);

    Order save(Order order);


    List<Order> findAllByCompany(Optional<Company> company);
}
