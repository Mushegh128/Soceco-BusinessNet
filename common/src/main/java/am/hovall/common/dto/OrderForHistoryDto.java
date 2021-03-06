package am.hovall.common.dto;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.entity.ProductOrder;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Setter
@Builder
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
