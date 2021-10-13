package am.hovall.common.services.impl;


import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.Product;
import am.hovall.common.model.entities.ProductCategory;
import am.hovall.common.repositories.BrandRepository;
import am.hovall.common.repositories.ProductCategoryRepository;
import am.hovall.common.repositories.ProductRepository;
import am.hovall.common.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        product.setActive(true);
        return productRepository.save(product);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> findAllByProductCategory(long id) {
        return productRepository.findAllByProductCategory(productCategoryRepository.findById(id).get());
    }

    @Override
    public List<Product> findAllByBrand(long id) {
        return productRepository.findAllByBrand(brandRepository.findById(id).get());
    }
}
