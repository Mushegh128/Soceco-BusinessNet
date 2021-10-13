package am.hovall.common.repositories;

import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.Product;
import am.hovall.common.model.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByProductCategory(ProductCategory category);

    List<Product> findAllByBrand(Brand brand);
}
