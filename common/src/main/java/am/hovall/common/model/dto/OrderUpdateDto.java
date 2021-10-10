package am.hovall.common.model.dto;

import am.hovall.common.model.entities.ProductOrder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderUpdateDto {

        private long id;
        private List<ProductOrder> productOrders;

}
