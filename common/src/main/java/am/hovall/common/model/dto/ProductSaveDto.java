package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.MadeInCountry;
import am.hovall.common.model.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSaveDto {

    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private LocalDateTime createdDateTime;
    private MadeInCountry maidInCountry;
    private ProductCategory productCategory;
    private Brand brand;

}
