package am.hovall.common.service.impl;

import am.hovall.common.entity.Product;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllByCategoryId(long id) {
        return productRepository.findAllByProductCategoryId(id);
    }

    @Override
    public List<Product> findAllByBrandId(long id) {
        return productRepository.findAllByBrandId(id);
    }

    @Override
    public List<Product> findAllByPriceRange(double startPrice, double endPrice) {
        return productRepository.findAllByPriceStartsAndPriceEnds(startPrice, endPrice);
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public boolean deactivate(long id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            return false;
        }
        byId.get().setActive(false);
        productRepository.save(byId.get());
        return true;
    }
}
