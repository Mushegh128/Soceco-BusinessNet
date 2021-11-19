package am.hovall.common.service;

import am.hovall.common.entity.PaymentStatus;
import am.hovall.common.request.PaymentRequest;
import am.hovall.common.request.PaymentsSearchRequest;
import am.hovall.common.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse save(PaymentRequest paymentRequest);

    List<PaymentResponse> findAllByCompanyRegisterNumber(String id);

    List<PaymentResponse> findAllByFromUser(Long id);

    PaymentResponse setPaymentStatusBySerialNumber(PaymentStatus paymentStatus, String serialNumber);

    List<PaymentResponse> findByStatus(PaymentStatus created);

    List<PaymentResponse> search(PaymentsSearchRequest paymentsSearchRequest);
}
