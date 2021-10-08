package am.hovall.rest.response;

import am.hovall.common.model.dto.ProductDto;
import am.hovall.common.model.entities.Company;
import lombok.Builder;
import lombok.Setter;

import java.util.List;
@Setter
@Builder
public class MainResponse {
    private Company distributorCompany;
    private List<ProductDto> productDtoList;
    private double companyTotalDebt;
}
