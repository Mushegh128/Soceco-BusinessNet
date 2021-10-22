package am.hovall.common.service.impl;

import am.hovall.common.entity.Order;
import am.hovall.common.entity.OrderStatus;
import am.hovall.common.exception.OrderNotFoundException;
import am.hovall.common.mapper.OrderMapper;
import am.hovall.common.repository.OrderRepository;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public Double getCompanyDebt(long companyId) {
        return orderRepository.findDebtSizeByCompanyId(companyId);
    }

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        final Order order = orderMapper.toEntity(orderRequest);
        final Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> findAllByCompanyId(Long id) {
        final List<Order> ordersByCompanyId = orderRepository.findAllByCompany_Id(id);
        return ordersByCompanyId.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        Optional<Order> orderOpt = orderRepository.findBySerialNumber(orderRequest.getSerialNumber());
        Order orderUpdated = orderOpt.orElseThrow(OrderNotFoundException::new);
        Order order = orderMapper.toEntity(orderRequest);
        orderUpdated.setProductOrders(order.getProductOrders());
        orderUpdated.setUser(order.getUser());
        orderUpdated.setCompany(order.getCompany());
        return orderMapper.toResponse(orderRepository.save(orderUpdated));
    }

    @Override
    public boolean changeOrderStatus(OrderStatus orderStatus, long serialNumber) {
        Optional<Order> orderOpt = orderRepository.findBySerialNumber(serialNumber);
        Order order = orderOpt.orElseThrow(OrderNotFoundException::new);
        switch (orderStatus) {
            case DELETED:
            case ORDERED:
                if (order.getOrderStatus() == OrderStatus.ORDERED || order.getOrderStatus() == OrderStatus.DELETED) {
                    order.setOrderStatus(orderStatus);
                }
                break;
            case SOLD_DEBT:
                if (order.getOrderStatus() == OrderStatus.ORDERED) {
                    order.setOrderStatus(orderStatus);
                }
                break;
            case SOLD_PAYED:
                if (order.getOrderStatus() == OrderStatus.SOLD_DEBT) {
                    order.setOrderStatus(orderStatus);
                }
                break;
        }
        if (order.getOrderStatus() == orderStatus) {
            return false;
        }
        orderRepository.save(order);
        return true;
    }
}
