package am.hovall.common.request;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductRequest {
    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private MadeInCountry madeInCountry;
    private ProductCategory productCategory;
    private Brand brand;
}
