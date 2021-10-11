package am.hovall.common.services.impl;


import am.hovall.common.model.entities.Product;
import am.hovall.common.repositories.ProductRepository;
import am.hovall.common.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
