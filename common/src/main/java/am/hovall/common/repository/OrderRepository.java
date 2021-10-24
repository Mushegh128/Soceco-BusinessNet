package am.hovall.common.repository;

import am.hovall.common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT SUM(debt_size) FROM order_basket WHERE company_id =:id AND debt_size > 0", nativeQuery = true)
    Double findDebtSizeByCompanyId(@Param("id") Long id);

    List<Order> findAllByCompany_Id(Long id);

    Optional<Order> findBySerialNumber(long serialNumber);

}
