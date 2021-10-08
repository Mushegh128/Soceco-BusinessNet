package am.hovall.common.model.dto;


import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Builder
public class PaymentDto {
    private long id;
    private double size;
    private LocalDateTime createdDateTime;
    private UserDto fromUserDto;
    private CompanyDto toCompanyDto;
    private OrderForHistoryDto orderDto;
}
