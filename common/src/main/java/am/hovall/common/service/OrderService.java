package am.hovall.common.service;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.entity.Company;
import am.hovall.common.entity.Order;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;

import java.util.List;

public interface OrderService {

    Double getCompanyDebt(Company company);

    OrderResponse save(OrderRequest orderRequest);

    List<OrderResponse> findAllByCompanyId(Long id);

    Order updateOrder(Order order);

    boolean changeOrderStatus(OrderStatus orderStatus);

    void delete(Long id);
}
