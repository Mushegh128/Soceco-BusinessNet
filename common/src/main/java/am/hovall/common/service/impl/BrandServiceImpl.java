package am.hovall.common.service.impl;

import am.hovall.common.entity.Brand;
import am.hovall.common.repository.BrandRepository;
import am.hovall.common.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }
}
