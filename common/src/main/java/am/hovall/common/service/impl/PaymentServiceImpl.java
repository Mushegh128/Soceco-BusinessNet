package am.hovall.common.service.impl;

import am.hovall.common.entity.Company;
import am.hovall.common.entity.Payment;
import am.hovall.common.entity.PaymentStatus;
import am.hovall.common.exception.BadSerialNumberException;
import am.hovall.common.exception.CompanyNotFoundException;
import am.hovall.common.mapper.PaymentMapper;
import am.hovall.common.repository.CompanyRepository;
import am.hovall.common.repository.OrderRepository;
import am.hovall.common.repository.PaymentRepository;
import am.hovall.common.request.PaymentRequest;
import am.hovall.common.request.PaymentsSearchRequest;
import am.hovall.common.response.PaymentResponse;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;

    @Override
    public PaymentResponse save(PaymentRequest paymentRequest) {
        Company company = companyRepository.findById(paymentRequest.getUserRequest().getCompanyRequest().getId()).
                orElseThrow(CompanyNotFoundException::new);
        Double debt = orderRepository.findDebtSizeByCompanyId(company.getId());
        Double paymentSize = paymentRequest.getSize();
        if (debt < paymentRequest.getSize()) {
            paymentSize = debt;
        }
        Payment payment = paymentMapper.toEntity(paymentRequest);
        payment.setSize(paymentSize);
        payment.setCreatedDateTime(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.CREATED);
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponse> findAllByCompanyRegisterNumber(String companyRegisterNumber) {
        return paymentRepository.findAllByCompanyRegisterNumber(companyRegisterNumber)
                .stream().map(paymentMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> findAllByFromUser(Long id) {
        return paymentRepository.findAllByFromUserId(id)
                .stream().map(paymentMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public PaymentResponse setPaymentStatusBySerialNumber(PaymentStatus paymentStatus, String serialNumber) {
        Payment payment = paymentRepository.findBySerialNumber(serialNumber).orElseThrow(BadSerialNumberException::new);
        payment.setPaymentStatus(paymentStatus);
        return paymentMapper.toResponse(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentResponse> findByStatus(PaymentStatus paymentStatus) {
        List<Payment> paymentList = paymentRepository.findByPaymentStatus(paymentStatus);
        return paymentList.stream().map(paymentMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<PaymentResponse> search(PaymentsSearchRequest paymentsSearchRequest) {
        List<Payment> payments= paymentRepository.search(
                paymentsSearchRequest.getRegisterNumber(),
                paymentsSearchRequest.getStatus(),
                paymentsSearchRequest.getStartLocalDateTime(),
                paymentsSearchRequest.getEndLocalDateTime());
        return payments.stream().map(paymentMapper::toResponse).collect(Collectors.toList());
    }
}
