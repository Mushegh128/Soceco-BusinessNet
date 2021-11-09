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
@RequestMapping("/payments")
public class PaymentEndpoint {
    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<PaymentResponse> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.save(paymentRequest));
    }

    @GetMapping("/company/{companyRegisterNumber}")
    public ResponseEntity<List<PaymentResponse>> getAllByCompany(
            @PathVariable("companyRegisterNumber") String companyRegisterNumber) {
        return ResponseEntity.ok(paymentService.findAllByCompanyRegisterNumber(companyRegisterNumber));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentResponse>> getAllByUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(paymentService.findAllByFromUser(id));
    }

}
