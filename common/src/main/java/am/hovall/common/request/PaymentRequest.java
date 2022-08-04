package am.hovall.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class PaymentRequest {
    @NotNull
    @DecimalMin(value = "0.1")
    private Double size;
    @NotNull
    private UserRequest userRequest;
    @NotNull @Digits(integer = 8, fraction = 0)
    private Long companyRegisterNumber;
}
