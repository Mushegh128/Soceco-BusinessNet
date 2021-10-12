package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.MadeInCountry;
import am.hovall.common.model.entities.ProductCategory;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ProductUpdateDto {
    private long id;
    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private double cashback;
    private boolean isActive;
    private MadeInCountry madeInCountry;
    private ProductCategory productCategory;
    private Brand brand;
}
