package am.hovall.common.mapper;


import am.hovall.common.entity.*;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.request.ProductOrderRequest;
import am.hovall.common.response.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper implements BaseMapper<Order, OrderRequest, OrderResponse> {
    private final ModelMapper mapper;
    private final ProductOrderMapper productOrderMapper;

    @Override
    public Order toEntity(OrderRequest orderRequest) {
        List<ProductOrder> productOrders = new LinkedList<>();
        for (ProductOrderRequest productOrderRequest : orderRequest.getProductOrderRequests()) {
            productOrders.add(productOrderMapper.toEntity(productOrderRequest));
        }
        Order order = mapper.map(orderRequest, Order.class);
        order.setProductOrders(productOrders);
        order.setCompany(mapper.map(orderRequest.getCompanyRequest(), Company.class));
        order.setUser(mapper.map(orderRequest.getUserRequest(), User.class));
        return order;
    }

    @Override
    public OrderResponse toResponse(Order order) {
        List<PaymentResponse> paymentResponseList = new LinkedList<>();
        List<ProductOrderResponse> productOrderResponseList = new LinkedList<>();
        for (ProductOrder productOrder : order.getProductOrders()) {
            productOrderResponseList.add(productOrderMapper.toResponse(productOrder));
        }
        for (Payment payment : order.getPaymentList()) {
            paymentResponseList.add(mapper.map(payment, PaymentResponse.class));
        }
        OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
        orderResponse.setProductOrderResponses(productOrderResponseList);
        orderResponse.setCompanyResponse(mapper.map(order.getCompany(), CompanyResponse.class));
        orderResponse.setUserResponse(mapper.map(order.getUser(), UserResponse.class));
        orderResponse.setPaymentResponseList(paymentResponseList);
        return orderResponse;
    }
}
