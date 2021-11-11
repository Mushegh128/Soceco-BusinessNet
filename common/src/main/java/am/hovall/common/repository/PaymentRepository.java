package am.hovall.common.repository;

import am.hovall.common.entity.Payment;
import am.hovall.common.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByCompanyRegisterNumber(Long companyRegisterNumber);

    List<Payment> findAllByFromUserId(Long id);

    Optional<Payment> findBySerialNumber(String serialNumber);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT * FROM payment WHERE company_register_number=:registerNumber AND payment_status=:status " +
            "AND created_date_time BETWEEN :startLocalDateTime AND :endLocalDateTime", nativeQuery = true)
    List<Payment> search(
            @Param("registerNumber") String registerNumber,
            @Param("status") PaymentStatus status,
            @Param("startLocalDateTime") LocalDateTime startLocalDateTime,
            @Param("endLocalDateTime") LocalDateTime endLocalDateTime);
}
