package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Company;
import am.hovall.common.model.entities.Order;
import am.hovall.common.model.entities.User;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class PaymentDto {
    private long id;
    private double size;
    private LocalDateTime createdDateTime;
    private UserDto fromUserDto;
    private CompanyDto toCompanyDto;
    private OrderForHistoryDto orderDto;
}
