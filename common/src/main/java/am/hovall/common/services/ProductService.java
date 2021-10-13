package am.hovall.common.services;


import am.hovall.common.model.entities.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product save(Product product);

    Product findById(long id);

    List<Product> findAllByProductCategory(long id);

    List<Product> findAllByBrand(long id);


}
