package am.hovall.common.service;

import am.hovall.common.entity.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    void save(String title);

    void modifyBrand(Long id, String title);

    void delete(Long id);
}
