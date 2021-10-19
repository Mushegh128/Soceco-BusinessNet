package am.hovall.common.response;

import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
public class MainResponse {
    private List<ProductResponse> productResponseList;
    private double companyTotalDebt;
}
