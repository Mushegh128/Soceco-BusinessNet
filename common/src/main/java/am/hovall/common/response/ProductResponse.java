package am.hovall.common.response;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private Long barcode;
    private String title;
    private String description;
    private Double price;
    private Double weight;
    private LocalDateTime createdDateTime;
    private boolean isActive;
    private String picUrl;
    private MadeInCountry madeInCountry;
    private Discount discount;
    private ProductCategory productCategory;
    private Brand brand;
}
