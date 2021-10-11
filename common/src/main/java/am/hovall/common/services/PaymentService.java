package am.hovall.common.services;

import am.hovall.common.model.entities.Payment;

import java.util.List;

public interface PaymentService {

    Payment save(Payment payment);

    List<Payment> findAllByCompanyId(Long id);

    List<Payment> findAllByFromUser(Long id);

}
