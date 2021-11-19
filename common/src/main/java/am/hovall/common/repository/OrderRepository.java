package am.hovall.common.repository;

import am.hovall.common.entity.Order;
import am.hovall.common.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT SUM(debt_size) FROM order_basket WHERE company_id =:id AND debt_size > 0", nativeQuery = true)
    Double findDebtSizeByCompanyId(@Param("id") Long id);

    List<Order> findAllByUserIdAndCompanyId(long userId, long companyId);

    @Query(value = "SELECT * from order_basket where is_synchronized = false", nativeQuery = true)
    List<Order> findAllUnSynchronized();

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    @Query(value = "select from order_basket where order_basket.created_date_time between :startDate  and :endDate", nativeQuery = true)
    List<Order> findAllByOrderCreatedDateTimeRange(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    Optional<Order> findBySerialNumber(long serialNumber);

    List<Order> findAllByCompany_Id(Long id);


    List<Order> findAllByCompanyIdAndOrderStatus(long companyId, OrderStatus orderStatus);

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
