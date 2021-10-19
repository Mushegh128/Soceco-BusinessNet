package am.hovall.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PaymentRequest {
    private double size;
    private UserRequest userRequest;
}
