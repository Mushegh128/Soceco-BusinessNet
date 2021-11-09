package am.hovall.common.response;

import am.hovall.common.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class OrderResponse {
    private long id;
    private long serialNumber;
    private UserResponse userResponse;
    private CompanyResponse companyResponse;
    private LocalDateTime createdDateTime;
    private LocalDateTime saleDateTime;
    private double orderCost;
    private double debtSize;
    private OrderStatus orderStatus;
    private List<ProductOrderResponse> productOrderResponses;
    private List<PaymentResponse> paymentResponseList;

    public double getOrderCost() {
        productOrderResponses.forEach(productOrderResponse -> orderCost +=
                productOrderResponse.getCount() * productOrderResponse.getProductResponse().getPrice());
        return orderCost;
    }
}
