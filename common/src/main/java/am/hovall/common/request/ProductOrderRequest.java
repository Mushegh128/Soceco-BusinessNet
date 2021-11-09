package am.hovall.common.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
public class ProductOrderRequest {
    @NotNull
    private ProductRequest productRequest;
    @Min(value = 1)
    private int count;
}
