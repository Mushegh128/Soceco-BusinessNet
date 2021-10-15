package am.hovall.common.request;

import am.hovall.common.dto.CompanyDto;
import am.hovall.common.dto.UserDto;
import am.hovall.common.entity.ProductOrder;
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
    private UserDto userDto;
    private CompanyDto companyDto;
    private double orderCost;
    private List<ProductOrder> productOrders;
}
