package am.hovall.common.mapper;


import am.hovall.common.mapper.config.BaseMapper;
import am.hovall.common.entity.Order;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMapper implements BaseMapper<Order, OrderRequest, OrderResponse> {
    private final ModelMapper mapper;

    @Override
    public Order toEntity(OrderRequest orderRequest) {
        return mapper.map(orderRequest, Order.class);
    }

    @Override
    public OrderResponse toResponse(Order order) {
        return mapper.map(order, OrderResponse.class);
    }
}
