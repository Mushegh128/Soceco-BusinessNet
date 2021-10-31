package am.hovall.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class ProductOrderResponse {
    @NotNull
    private Long id;
    @NotNull
    private ProductResponse productResponse;
    @NotNull
    @Min(value = 1)
    private int count;
}
