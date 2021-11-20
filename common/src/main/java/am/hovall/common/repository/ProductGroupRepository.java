package am.hovall.common.repository;

import am.hovall.common.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

    ProductGroup findByTitle(String title);
}
