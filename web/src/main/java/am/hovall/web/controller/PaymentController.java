package am.hovall.web.controller;

import am.hovall.common.entity.PaymentStatus;
import am.hovall.common.request.PaymentsSearchRequest;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public String payments(ModelMap modelMap){
        modelMap.addAttribute("payments", paymentService.findByStatus(PaymentStatus.CREATED));
        modelMap.addAttribute("paymentsSearchRequest",new PaymentsSearchRequest());
        return "payments";
    }

    @PostMapping("/{serialNumber}/{status}")
    public String status(@PathVariable("serialNumber") String serialNumber, @PathVariable("status") int status){
        if (status == 1){
            paymentService.setPaymentStatusBySerialNumber(PaymentStatus.CONFIRMED, serialNumber);
        }else if (status == 0){
            paymentService.setPaymentStatusBySerialNumber(PaymentStatus.DENIED, serialNumber);
        }
        return "redirect:/payments";
    }

    @GetMapping("/{registerNumber}")
    public String byCompany(@PathVariable("registerNumber") String registerNumber, ModelMap modelMap){
        modelMap.addAttribute("payments", paymentService.findAllByCompanyRegisterNumber(registerNumber));
        return "payments";
    }

    @GetMapping("/user/{id}")
    public String byUser(@PathVariable("id") Long id, ModelMap modelMap){
        modelMap.addAttribute("payments", paymentService.findAllByFromUser(id));
        return "payments";
    }

    @PostMapping("/search")
    public String search(ModelMap modelMap, @ModelAttribute PaymentsSearchRequest paymentsSearchRequest){
        modelMap.addAttribute("payments", paymentService.search(paymentsSearchRequest));
        return "payments";
    }

}

