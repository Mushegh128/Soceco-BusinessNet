package am.hovall.common.repositories;

import am.hovall.common.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT SUM(debt_size) FROM order_basket WHERE company_id =:id AND debt_size > 0", nativeQuery = true)
    Double findDebtSizeByCompanyId(@Param("id") Long id);

}
