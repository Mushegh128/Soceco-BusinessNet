package am.hovall.common.request;

import am.hovall.common.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsSearchRequest {

    @NotBlank
    String registerNumber;
    LocalDateTime startLocalDateTime;
    LocalDateTime endLocalDateTime;
    @NotNull
    PaymentStatus status;

}
