package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.OrderCreateDto;
import am.hovall.common.model.dto.OrderForHistoryDto;
import am.hovall.common.model.entities.Order;
import am.hovall.common.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderEndpoint {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PutMapping("orders/add")
    public ResponseEntity<OrderForHistoryDto> addOrder(@RequestBody OrderCreateDto orderCreateDto){
        Order order = modelMapper.map(orderCreateDto, Order.class);
        Order savedOrder = orderService.save(order);
        return ResponseEntity.ok(modelMapper.map(savedOrder, OrderForHistoryDto.class));
    }



}
