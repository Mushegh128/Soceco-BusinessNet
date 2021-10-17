package am.hovall.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ProductOrderRequest {
    private ProductRequest productRequest;
    private int count;
}
