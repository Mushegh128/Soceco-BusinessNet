package am.hovall.common.service.impl;

import am.hovall.common.entity.ProductCategory;
import am.hovall.common.entity.ProductGroup;
import am.hovall.common.repository.ProductCategoryRepository;
import am.hovall.common.repository.ProductGroupRepository;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductGroupRepository productGroupRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        ProductCategory byTitle = productCategoryRepository.findByTitle(productCategory.getTitle());
        if (byTitle == null) {
            productCategoryRepository.save(productCategory);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void modifyProductCategory(Long id, String title, String group) {
        Optional<ProductCategory> byId = productCategoryRepository.findById(id);
        ProductCategory productCategory = new ProductCategory();
        ProductCategory byTitle = productCategoryRepository.findByTitle(title);
        ProductGroup productGroup = productGroupRepository.findByTitle(group);
        if (byId.isPresent()) {
            productCategory = byId.get();
        }
        if (!productCategory.getTitle().equals(title) && byTitle == null) {
            productCategory.setTitle(title);
        }
        productCategory.setProductGroup(Objects.requireNonNullElseGet(productGroup, () -> new ProductGroup(group)));

        if (productCategory.getProductGroup() == null && productGroup == null) {
            productCategory.setProductGroup(new ProductGroup(group));
        }
        productCategoryRepository.save(productCategory);
    }

    @Override
    public void delete(Long id) {
        if (productCategoryRepository.findById(id).isPresent()) {
            productRepository.changeProductCategoryNullWhenCategoryDeleted(id, null);
            productCategoryRepository.deleteById(id);
        }
    }

}
