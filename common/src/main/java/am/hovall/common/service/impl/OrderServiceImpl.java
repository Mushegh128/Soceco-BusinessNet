package am.hovall.common.service.impl;

import am.hovall.common.entity.Order;
import am.hovall.common.entity.OrderStatus;
import am.hovall.common.exception.OrderNotFoundException;
import am.hovall.common.mapper.OrderMapper;
import am.hovall.common.repository.OrderRepository;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.CompanyResponse;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.service.CompanyService;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CompanyService companyService;
    private final OrderMapper orderMapper;

    @Override
    public Double getCompanyDebt(long registerNumber) {
        final CompanyResponse companyResponse = companyService.findByRegisterNumber(registerNumber);
        return companyResponse.getRating();
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
        return null;
    }

    @Override
    public boolean changeOrderStatus(OrderStatus orderStatus) {
        return false;
    }

    @Override
    public void delete(Long id) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
        order.setOrderStatus(OrderStatus.DELETED);
        orderRepository.save(order);
    }
}
