package am.hovall.common.service.impl;

import am.hovall.common.entity.ProductGroup;
import am.hovall.common.repository.ProductCategoryRepository;
import am.hovall.common.repository.ProductGroupRepository;
import am.hovall.common.service.ProductGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductGroupServiceImpl implements ProductGroupService {

    private final ProductGroupRepository productGroupRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductGroup> findAll() {
        return productGroupRepository.findAll();
    }

    @Override
    public void save(String title) {
        productGroupRepository.save(new ProductGroup(title));
    }

    @Override
    public void modifyProductGroup(Long id, String title) {
        Optional<ProductGroup> byId = productGroupRepository.findById(id);
        ProductGroup byTitle = productGroupRepository.findByTitle(title);
        if (byId.isEmpty() && byTitle == null) {
            productGroupRepository.save(new ProductGroup(title));
        } else if (byId.isPresent()) {
            ProductGroup productGroup = byId.get();
            productGroup.setTitle(title);
            productGroupRepository.save(productGroup);
        }
    }

    @Override
    public void delete(Long id) {
        if (productGroupRepository.findById(id).isPresent()) {
            productCategoryRepository.changeProductGroupNullWhenGroupDeleted(id, null);
            productGroupRepository.deleteById(id);
        }
    }
}
