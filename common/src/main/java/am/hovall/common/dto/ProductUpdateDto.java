package am.hovall.common.dto;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.MadeInCountry;
import am.hovall.common.entity.ProductCategory;

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
