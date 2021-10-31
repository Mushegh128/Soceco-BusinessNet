package am.hovall.common.response;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    @NonNull
    private Long id;
    @NotNull
    private Long barcode;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    @DecimalMin(value = "0.0")
    private Double price;
    @DecimalMin(value = "0.0")
    private Double weight;
    private LocalDateTime createdDateTime;
    private boolean isActive;
    private MadeInCountry maidInCountry;
    private Discount discount;
    @NotNull
    private ProductCategory productCategory;
    @NotNull
    private Brand brand;
}
