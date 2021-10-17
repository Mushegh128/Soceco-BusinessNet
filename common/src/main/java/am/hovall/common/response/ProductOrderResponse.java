package am.hovall.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductOrderResponse {

    private long id;
    private ProductResponse productResponse;
    private int count;
}
