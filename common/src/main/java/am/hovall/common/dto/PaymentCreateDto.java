package am.hovall.common.dto;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class PaymentCreateDto {

    private double size;
    private UserDto fromUserDto;

}
