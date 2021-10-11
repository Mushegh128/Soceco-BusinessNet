package am.hovall.common.repositories;

import am.hovall.common.model.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByTitle(String title);

}
