package am.hovall.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private long serialNumber;
    private UserRequest userRequest;
    private CompanyRequest companyRequest;
    private double orderCost;
    private List<ProductOrderRequest> productOrderRequests;
}
