package am.hovall.common.response;

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
    private LocalDateTime createdDateTime;
    private UserResponse userResponse;
    private boolean isConfirmed;
}
