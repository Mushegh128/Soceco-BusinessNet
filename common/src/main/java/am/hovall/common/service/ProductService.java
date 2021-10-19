package am.hovall.common.service;


import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    List<ProductResponse> findAllByCategory(ProductCategory productCategory);

    List<ProductResponse> findAllByBrand(Brand brand);

    List<ProductResponse> findAllByPriceRange(Double startPrice, Double endPrice);

    ProductResponse add(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    boolean deactivate(Long id);
}
