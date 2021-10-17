package am.hovall.common.mapper;


import am.hovall.common.entity.*;
import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.request.ProductOrderRequest;
import am.hovall.common.response.CompanyResponse;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.response.PaymentResponse;
import am.hovall.common.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper implements BaseMapper<Order, OrderRequest, OrderResponse> {
    private final ModelMapper mapper;

    @Override
    public Order toEntity(OrderRequest orderRequest) {
        List<ProductOrderRequest> productOrderRequests = orderRequest.getProductOrderRequests();
        List<ProductOrder> productOrders = new LinkedList<>();
        for (ProductOrderRequest productOrderRequest : productOrderRequests) {
            ProductOrder productOrder = mapper.map(productOrderRequest, ProductOrder.class);
            productOrder.setProduct(mapper.map(productOrderRequest.getProductRequest(), Product.class));
            productOrders.add(productOrder);
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
        for (Payment payment : order.getPaymentList()) {
            paymentResponseList.add(mapper.map(payment, PaymentResponse.class));
        }
        OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
        orderResponse.setCompanyResponse(mapper.map(order.getCompany(), CompanyResponse.class));
        orderResponse.setUserResponse(mapper.map(order.getUser(), UserResponse.class));
        orderResponse.setPaymentResponseList(paymentResponseList);
        return orderResponse;
    }
}
