package am.hovall.common.repository;

import am.hovall.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByProductCategoryId(long id);

    List<Product> findAllByBrandId(long id);

    @Query(value = "select * FROM product where price>=:startPrice and price<=:endPrice", nativeQuery = true)
    List<Product> findAllByPriceStartsAndPriceEnds(@Param("startPrice") double startPrice,
                                                   @Param("endPrice") double endPrice);

}
