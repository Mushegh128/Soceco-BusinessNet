package am.hovall.common.repository;

import am.hovall.common.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByProductCategoryId(long id, PageRequest pageRequest);

    List<Product> findAllByBrandId(long id, PageRequest pageRequest);

    @Query(value = "select * FROM product where price>=:startPrice and price<=:endPrice", nativeQuery = true)
    List<Product> findAllByPriceStartsAndPriceEnds(@Param("startPrice") double startPrice,
                                                   @Param("endPrice") double endPrice);


    Optional<Product> findByBarcode(long barcode);
}
