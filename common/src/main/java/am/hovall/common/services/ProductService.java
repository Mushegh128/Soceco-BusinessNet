package am.hovall.common.services;


import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.Product;
import am.hovall.common.model.entities.ProductCategory;

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
