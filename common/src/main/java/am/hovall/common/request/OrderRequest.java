package am.hovall.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private long serialNumber;
    @NotNull
    private UserRequest userRequest;
    @NotNull
    private CompanyRequest companyRequest;
    private double orderCost;
    @NotEmpty
    private List<ProductOrderRequest> productOrderRequests;
}
