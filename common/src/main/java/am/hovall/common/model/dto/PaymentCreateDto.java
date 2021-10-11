package am.hovall.common.model.dto;

import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
public class PaymentCreateDto {

    private double size;
    private UserDto fromUserDto;

}
