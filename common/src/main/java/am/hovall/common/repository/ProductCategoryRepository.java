package am.hovall.common.repository;

import am.hovall.common.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    ProductCategory findByTitle(String title);

    @Modifying
    @Query(value = "UPDATE product_category SET product_group_id=:nullValue WHERE product_group_id=:id", nativeQuery = true)
    void changeProductGroupNullWhenGroupDeleted(@Param("id") Long id, @Param("nullValue") Integer nullValue);

}
