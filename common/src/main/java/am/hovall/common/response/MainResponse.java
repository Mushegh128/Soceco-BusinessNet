package am.hovall.common.response;

import lombok.Builder;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Builder
public class MainResponse {
    @NotNull
    private List<ProductResponse> productResponseList;
    @DecimalMin("0.0")
    private double companyTotalDebt;
}
