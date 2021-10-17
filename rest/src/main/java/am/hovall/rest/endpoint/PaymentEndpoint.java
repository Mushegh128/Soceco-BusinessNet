package am.hovall.rest.endpoint;

import am.hovall.common.entity.Payment;
import am.hovall.common.mapper.PaymentMapper;
import am.hovall.common.request.PaymentRequest;
import am.hovall.common.response.PaymentResponse;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentEndpoint {
    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentMapper.toResponse(paymentService.save(paymentMapper.toEntity(paymentRequest))));
    }

    @GetMapping("/payments/company/{id}")
    public ResponseEntity<List<PaymentResponse>> getAllByCompany(@PathVariable("id") Long id) {
        List<Payment> paymentList = paymentService.findAllByCompanyId(id);
        return ResponseEntity.ok(parseToPaymentResponse(paymentList));
    }

    @GetMapping("/payments/user/{id}")
    public ResponseEntity<List<PaymentResponse>> getAllByUser(@PathVariable("id") Long id) {
        List<Payment> paymentList = paymentService.findAllByFromUser(id);
        return ResponseEntity.ok(parseToPaymentResponse(paymentList));
    }

    private List<PaymentResponse> parseToPaymentResponse(List<Payment> paymentList) {
        List<PaymentResponse> paymentResponseList = new LinkedList<>();
        for (Payment payment : paymentList) {
            paymentResponseList.add(paymentMapper.toResponse(payment));
        }
        return paymentResponseList;
    }

}
