package am.hovall.rest.response;

import am.hovall.common.dto.ProductDto;
import lombok.Builder;
import lombok.Setter;

import java.util.List;
@Setter
@Builder
public class MainResponse {
    private List<ProductDto> productDtoList;
    private double companyTotalDebt;
}
