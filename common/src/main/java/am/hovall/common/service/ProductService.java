package am.hovall.common.service;


import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product findById(long id);

    List<Product> findAllByCategory(ProductCategory productCategory);

    List<Product> findAllByBrand(Brand brand);

    List<Product> findAllByPriceRange(Double startPrice, Double endPrice);

    Product add(Product map);

    Product update(Product product);

    boolean deactivate(Long id);

    void saveImage(MultipartFile file, Product product) throws IOException;
}
