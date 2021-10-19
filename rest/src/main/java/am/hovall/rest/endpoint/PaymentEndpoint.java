package am.hovall.rest.endpoint;

import am.hovall.common.request.PaymentRequest;
import am.hovall.common.response.PaymentResponse;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentEndpoint {
    private final PaymentService paymentService;


    @PostMapping("/payments")
    public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.save(paymentRequest));
    }

    @GetMapping("/payments/company/{id}")
    public ResponseEntity<List<PaymentResponse>> getAllByCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.findAllByCompanyId(id));
    }

    @GetMapping("/payments/user/{id}")
    public ResponseEntity<List<PaymentResponse>> getAllByUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.findAllByFromUser(id));
    }

}
