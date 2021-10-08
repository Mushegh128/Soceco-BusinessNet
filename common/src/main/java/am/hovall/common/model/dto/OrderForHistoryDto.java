package am.hovall.common.model.dto;

import am.hovall.common.model.OrderStatus;
import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Payment;
import am.hovall.common.model.entities.ProductOrder;
import am.hovall.common.model.entities.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderForHistoryDto {

    private long id;
    private UserDto userDto;
    private CompanyDto companyDto;
    private LocalDateTime createdDateTime;
    private LocalDateTime saleDateTime;
    private double orderCost;
    private double debtSize;
    private OrderStatus orderStatus;
    private List<ProductOrder> productOrders;
    private List<PaymentDto> paymentDtoList;
}
