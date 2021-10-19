package am.hovall.common.response;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private long id;
    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private double cashback;
    private LocalDateTime createdDateTime;
    private boolean isActive;
    private MadeInCountry maidInCountry;
    private Discount discount;
    private ProductCategory productCategory;
    private Brand brand;
}
