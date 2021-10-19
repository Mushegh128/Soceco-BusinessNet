package am.hovall.common.service;

import am.hovall.common.request.PaymentRequest;
import am.hovall.common.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse save(PaymentRequest paymentRequest);

    List<PaymentResponse> findAllByCompanyId(Long id);

    List<PaymentResponse> findAllByFromUser(Long id);

}
