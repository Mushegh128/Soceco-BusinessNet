package am.hovall.common.repositories;

import am.hovall.common.model.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByTitle(String title);

}
