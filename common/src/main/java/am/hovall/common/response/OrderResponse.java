package am.hovall.common.response;

import am.hovall.common.entity.OrderStatus;
import lombok.*;

import javax.validation.constraints.Digits;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    @Digits(integer = 8, fraction = 0)
    private Long serialNumber;
    private UserResponse userResponse;
    private CompanyResponse companyResponse;
    private LocalDateTime createdDateTime;
    private LocalDateTime saleDateTime;
    private Double orderCost;
    private Double debtSize;
    private OrderStatus orderStatus;
    private List<ProductOrderResponse> productOrderResponses;
    private List<PaymentResponse> paymentResponseList;

    public double getOrderCost() {
        productOrderResponses.forEach(productOrderResponse -> orderCost +=
                productOrderResponse.getCount() * productOrderResponse.getProductResponse().getPrice());
        return orderCost;
    }
}
