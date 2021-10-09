package am.hovall.common.model.dto;

import am.hovall.common.model.entities.ProductOrder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateDto {

    private UserDto userDto;
    private CompanyDto companyDto;
    private double orderCost;
    private List<ProductOrder> productOrders;

    public double getOrderCost() {
        for (ProductOrder productOrder : productOrders) {
            orderCost += productOrder.getCount() * (productOrder.getProduct().getPrice() * companyDto.getDiscount().getSize()) / 100;
        }
        return orderCost;
    }
}
