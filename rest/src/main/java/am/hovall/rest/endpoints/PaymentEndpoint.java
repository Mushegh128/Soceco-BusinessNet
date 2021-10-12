package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.PaymentCreateDto;
import am.hovall.common.model.dto.PaymentDto;
import am.hovall.common.model.dto.UserDto;
import am.hovall.common.model.entities.Payment;
import am.hovall.common.model.entities.User;
import am.hovall.common.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentEndpoint {
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @PostMapping("/payments")
    public ResponseEntity<PaymentDto> doPayment(@RequestBody PaymentCreateDto paymentCreateDto){
        Payment payment = modelMapper.map(paymentCreateDto, Payment.class);
        Payment savedPayment = paymentService.save(payment);

        return ResponseEntity.ok(parseToPaymentDto(savedPayment));
    }

    @GetMapping("/payments/company/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByCompany(@PathVariable("id") Long id){
        List<Payment> paymentList = paymentService.findAllByCompanyId(id);
        List<PaymentDto> paymentDtoList = new LinkedList<>();
        for (Payment payment : paymentList) {
            paymentDtoList.add(parseToPaymentDto(payment));
        }
            return ResponseEntity.ok(paymentDtoList);
    }

    @GetMapping("/payments/user/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByUser(@PathVariable("id") Long id){
        List<Payment> paymentList = paymentService.findAllByFromUser(id);
        List<PaymentDto> paymentDtoList = new LinkedList<>();
        for (Payment payment : paymentList) {
            paymentDtoList.add(parseToPaymentDto(payment));
        }
        return ResponseEntity.ok(paymentDtoList);
    }

    private PaymentDto parseToPaymentDto(Payment payment){
        User user = payment.getFromUser();
        PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
        paymentDto.setFromUserDto(modelMapper.map(user, UserDto.class));
        return paymentDto;
    }

}
