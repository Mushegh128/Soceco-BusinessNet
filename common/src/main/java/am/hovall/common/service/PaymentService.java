package am.hovall.common.service;

import am.hovall.common.entity.Payment;

import java.util.List;

public interface PaymentService {

    Payment save(Payment payment);

    List<Payment> findAllByCompanyId(Long id);

    List<Payment> findAllByFromUser(Long id);

}
