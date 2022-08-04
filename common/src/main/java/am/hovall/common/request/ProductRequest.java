package am.hovall.common.request;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private long id;
    @NotNull
    private Long barcode;
    @NotBlank
    private String title;
    private String description;
    @NotNull
    @DecimalMin(value = "0.1")
    private Double price;
    @DecimalMin(value = "0.001")
    private Double weight;
    private MadeInCountry madeInCountry;
    @NotNull
    private ProductCategory productCategory;
    @NotNull
    private Brand brand;
}
