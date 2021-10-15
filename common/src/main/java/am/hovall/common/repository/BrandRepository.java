package am.hovall.common.repository;

import am.hovall.common.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByTitle(String title);

}
