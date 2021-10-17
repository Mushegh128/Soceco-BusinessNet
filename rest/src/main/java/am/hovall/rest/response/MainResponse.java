package am.hovall.rest.response;

import am.hovall.common.response.ProductResponse;
import lombok.Builder;
import lombok.Setter;

import java.util.List;
@Setter
@Builder
public class MainResponse {
    private List<ProductResponse> productResponseList;
    private double companyTotalDebt;
}
