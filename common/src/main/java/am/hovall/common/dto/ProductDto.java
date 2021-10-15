package am.hovall.common.dto;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Discount;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Builder
public class ProductDto {
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
