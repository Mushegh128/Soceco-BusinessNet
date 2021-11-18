package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.repository.BrandRepository;
import am.hovall.common.repository.ProductRepository;
import am.hovall.common.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public void save(String title) {
        brandRepository.save(new Brand(title));
    }

    @Override
    public void modifyBrand(Long id, String title) {
        Optional<Brand> byId = brandRepository.findById(id);
        Brand byTitle = brandRepository.findByTitle(title);
        if (byId.isEmpty() && byTitle == null) {
            brandRepository.save(new Brand(title));
        } else if (byId.isPresent()) {
            Brand brand = byId.get();
            brand.setTitle(title);
            brandRepository.save(brand);
        }
    }

    @Override
    public void delete(Long id) {
        if (brandRepository.findById(id).isPresent()) {
            productRepository.changeProductBrandNullWhenCBrandDeleted(id, null);
            brandRepository.deleteById(id);
        }
    }
}
