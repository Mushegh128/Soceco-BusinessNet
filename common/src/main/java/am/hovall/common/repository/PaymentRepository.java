package am.hovall.common.repository;

import am.hovall.common.entity.Payment;
import am.hovall.common.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByCompanyRegisterNumber(Long companyRegisterNumber);

    List<Payment> findAllByFromUserId(Long id);

    Optional<Payment> findBySerialNumber(String serialNumber);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}
