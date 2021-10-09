package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.*;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;
import am.hovall.common.model.entities.Payment;
import am.hovall.common.services.CompanyService;
import am.hovall.common.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class OrderEndpoint {

    private final OrderService orderService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @PostMapping("/orders")
    public ResponseEntity<List<OrderForHistoryDto>> getOrdersByCompany(@RequestBody CompanyDto companyDto){
        Optional<Company> company = companyService.findById(companyDto.getId());
        if (company.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<Order> allByCompany = orderService.findAllByCompany(company);
        List<OrderForHistoryDto> dtoList = new LinkedList<>();
        for (Order order : allByCompany) {
            dtoList.add(parseToOrderForHistoryDto(order));
        }
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/orders/add")
    public ResponseEntity<OrderForHistoryDto> addOrder(@RequestBody OrderCreateDto orderCreateDto){
        Order order = modelMapper.map(orderCreateDto, Order.class);
        Order savedOrder = orderService.save(order);
        return ResponseEntity.ok(modelMapper.map(savedOrder, OrderForHistoryDto.class));
    }

    private OrderForHistoryDto parseToOrderForHistoryDto(Order order){
        CompanyDto companyDto = modelMapper.map(order.getCompany(), CompanyDto.class);
        UserDto userDto = modelMapper.map(order.getUser(), UserDto.class);
        List<Payment> paymentList = order.getPaymentList();
        List<PaymentDto> paymentDtoList = new LinkedList<>();
        for (Payment payment : paymentList) {
            paymentDtoList.add(modelMapper.map(payment, PaymentDto.class));
        }
        OrderForHistoryDto orderForHistoryDto = modelMapper.map(order, OrderForHistoryDto.class);
        orderForHistoryDto.setCompanyDto(companyDto);
        orderForHistoryDto.setUserDto(userDto);
        return orderForHistoryDto;
    }



}
