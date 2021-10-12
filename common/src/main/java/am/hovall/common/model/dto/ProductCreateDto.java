package am.hovall.common.model.dto;

import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.MadeInCountry;
import am.hovall.common.model.entities.ProductCategory;

public class ProductCreateDto {

    private long barcode;
    private String title;
    private String description;
    private double price;
    private double weight;
    private MadeInCountry madeInCountry;
    private ProductCategory productCategory;
    private Brand brand;

}
