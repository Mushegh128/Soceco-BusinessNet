package am.hovall.common.service;


import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Product> findAllByCategoryId(long id);

    List<Product> findAllByBrandId(long id);

    List<Product> findAllByPriceRange(double startPrice, double endPrice);

    Product add(Product product);

    Product update(Product product);

    boolean deactivate(long id);
}
