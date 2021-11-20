package am.hovall.common.service;

import am.hovall.common.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> findAll();

    void addProductCategory(ProductCategory productCategory);

    void modifyProductCategory(Long id, String title, String group);

    void delete(Long id);

}
