package am.hovall.common.response;

import am.hovall.common.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class OrderResponse {
    @NotNull
    private Long id;
    @NotNull
    @Digits(integer = 8, fraction = 0)
    private Long serialNumber;
    @NotNull
    private UserResponse userResponse;
    @NotNull
    private CompanyResponse companyResponse;
    @CreatedDate
    @NotNull
    private LocalDateTime createdDateTime;
    private LocalDateTime saleDateTime;
    @DecimalMin(value = "0.0")
    private Double orderCost;
    @DecimalMin(value = "0.0")
    private Double debtSize;
    @NotNull
    private OrderStatus orderStatus;
    @NotEmpty
    private List<ProductOrderResponse> productOrderResponses;
    private List<PaymentResponse> paymentResponseList;
}
