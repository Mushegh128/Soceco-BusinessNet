package am.hovall.common.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResponse {
    private Long id;
    private ProductResponse productResponse;
    private int count;
}
