package am.hovall.common.response;

import am.hovall.common.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class PaymentResponse {
    private long id;
    private double size;
    private long companyRegisterNumber;
    private String serialNumber;
    private LocalDateTime createdDateTime;
    private UserResponse userResponse;
    private PaymentStatus paymentStatus;

}
