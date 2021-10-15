package am.hovall.common.service;


import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> findAllByCategory(ProductCategory productCategory);

    List<Product> findAllByBrand(Brand brand);

    List<Product> findAllByPriceRange(Double startPrice, Double endPrice);

    Product add(Product map);

    Product update(Product product);

    boolean deactivate(Long id);
}
