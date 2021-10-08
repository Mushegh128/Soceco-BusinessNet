package am.hovall.common.repositories;

import am.hovall.common.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, BigDecimal> {

}
