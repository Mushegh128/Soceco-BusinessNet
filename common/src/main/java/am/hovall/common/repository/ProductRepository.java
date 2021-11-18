package am.hovall.common.repository;

import am.hovall.common.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByProductCategoryId(long id, Pageable pageable);

    List<Product> findAllByBarcode(long id);

    List<Product> findAllByBrandId(long id, Pageable pageable);

    @Query(value = "select * FROM product where price>=:startPrice and price<=:endPrice", nativeQuery = true)
    List<Product> findAllByPriceStartsAndPriceEnds(@Param("startPrice") double startPrice,
                                                   @Param("endPrice") double endPrice, Pageable pageable);


    Optional<Product> findByBarcode(long barcode);

    @Query(value = "SELECT * from product where is_synchronized = false", nativeQuery = true)
    List<Product> findAllUnSynchronized();

    @Modifying
    @Query(value = "UPDATE product SET product_category_id=:nullValue WHERE product_category_id=:id", nativeQuery = true)
    void changeProductCategoryNullWhenCategoryDeleted(@Param("id") Long id, @Param("nullValue") Integer nullValue);

    @Modifying
    @Query(value = "UPDATE product SET brand_id=:nullValue WHERE brand_id=:id", nativeQuery = true)
    void changeProductBrandNullWhenCBrandDeleted(@Param("id") Long id, @Param("nullValue") Integer nullValue);

}
