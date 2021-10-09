package am.hovall.common.services;

import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;

public interface OrderService {

    public Double getCompanyDebt(Company company);

    Order save(Order order);


}
