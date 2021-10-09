package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Discount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CompanyDto {
    private Discount discount;

}
