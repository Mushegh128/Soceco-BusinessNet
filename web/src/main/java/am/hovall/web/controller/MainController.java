package am.hovall.web.controller;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.entity.PaymentStatus;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @GetMapping
    public String home(ModelMap modelMap){
        modelMap.addAttribute("orders", orderService.findByStatus(OrderStatus.ORDERED));
        modelMap.addAttribute("payment", paymentService.findByStatus(PaymentStatus.CREATED));
        return "index";
    }

}
