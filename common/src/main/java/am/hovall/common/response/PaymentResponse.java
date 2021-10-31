package am.hovall.common.response;

import am.hovall.common.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class PaymentResponse {
    @NotNull
    private Long id;
    @DecimalMin(value = "0.0")
    private Double size;
    @NotNull
    @Digits(integer = 8, fraction = 0)
    private Long companyRegisterNumber;
    private String serialNumber;
    @NotNull
    private LocalDateTime createdDateTime;
    @NotNull
    private UserResponse userResponse;
    @NotNull
    private PaymentStatus paymentStatus;

}
