package am.hovall.rest.endpoint;

import am.hovall.common.dto.PaymentCreateDto;
import am.hovall.common.dto.PaymentDto;
import am.hovall.common.dto.UserDto;
import am.hovall.common.entity.Payment;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentEndpoint {
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<PaymentDto> doPayment(@RequestBody PaymentCreateDto paymentCreateDto) {
        Payment savedPayment = paymentService.save(modelMapper.map(paymentCreateDto, Payment.class));
        return ResponseEntity.ok(modelMapper.map(savedPayment, PaymentDto.class));
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByCompany(@PathVariable("id") Long id) {
        List<Payment> paymentList = paymentService.findAllByCompanyId(id);
        return ResponseEntity.ok(parseToPaymentDto(paymentList));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentDto>> getAllByUser(@PathVariable("id") Long id) {
        List<Payment> paymentList = paymentService.findAllByFromUser(id);
        return ResponseEntity.ok(parseToPaymentDto(paymentList));
    }

    private List<PaymentDto> parseToPaymentDto(List<Payment> paymentList) {
        List<PaymentDto> paymentDtoList = paymentList.stream().map(payment ->
                modelMapper.map(payment, PaymentDto.class)).collect(Collectors.toList());
        paymentDtoList.forEach(paymentDto ->
                paymentDto.setFromUserDto(modelMapper.map(paymentList.stream().findFirst().get().getFromUser(), UserDto.class))
        );
        return paymentDtoList;
    }

}
