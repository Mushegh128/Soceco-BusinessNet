package am.hovall.common.service.impl;

import am.hovall.common.entity.ProductCategory;
import am.hovall.common.repository.ProductCategoryRepository;
import am.hovall.common.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }
}
