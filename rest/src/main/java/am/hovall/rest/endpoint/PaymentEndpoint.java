package am.hovall.rest.endpoint;

import am.hovall.common.dto.PaymentCreateDto;
import am.hovall.common.dto.PaymentDto;
import am.hovall.common.dto.UserDto;
import am.hovall.common.entity.Payment;
import am.hovall.common.entity.User;
import am.hovall.common.service.PaymentService;
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
        return ResponseEntity.ok(modelMapper.map(savedPayment, PaymentDto.class));
    }

    @GetMapping("/payments/company/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByCompany(@PathVariable("id") Long id){
        List<Payment> paymentList = paymentService.findAllByCompanyId(id);
            return ResponseEntity.ok(parseToPaymentDto(paymentList));
    }

    @GetMapping("/payments/user/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByUser(@PathVariable("id") Long id){
        List<Payment> paymentList = paymentService.findAllByFromUser(id);
        return ResponseEntity.ok(parseToPaymentDto(paymentList));
    }

    private List<PaymentDto> parseToPaymentDto(List<Payment> paymentList){
        List<PaymentDto> paymentDtoList = new LinkedList<>();
        for (Payment payment : paymentList) {
            User user = payment.getFromUser();
            PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
            paymentDto.setFromUserDto(modelMapper.map(user, UserDto.class));
            paymentDtoList.add(paymentDto);
        }
        return paymentDtoList;
    }

}
