package am.hovall.common.dto;

import am.hovall.common.entity.ProductOrder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderUpdateDto {

        private long id;
        private List<ProductOrder> productOrders;

}
