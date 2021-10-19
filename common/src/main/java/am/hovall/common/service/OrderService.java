package am.hovall.common.service;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;

import java.util.List;

public interface OrderService {

    Double getCompanyDebt(long registerNumber);

    OrderResponse save(OrderRequest orderRequest);

    List<OrderResponse> findAllByCompanyId(Long id);

    OrderResponse updateOrder(OrderRequest order);

    boolean changeOrderStatus(OrderStatus orderStatus);

    void delete(Long id);
}
