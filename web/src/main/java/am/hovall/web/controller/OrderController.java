package am.hovall.web.controller;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String orders(ModelMap modelMap){
        modelMap.addAttribute("orders", orderService.findAll());
        return "orders";
    }

    @PostMapping("/{serialNumber}")
    public String singleOrder(ModelMap modelMap, @PathVariable("serialNumber") long serialNumber){
        modelMap.addAttribute("order", orderService.findById(serialNumber));
        return "singleOrder";
    }

    @PostMapping("/submit/{serialNumber}")
    public String submit(@PathVariable("serialNumber") long serialNumber){
        orderService.changeOrderStatus(OrderStatus.SOLD_DEBT, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }

    @DeleteMapping("/{serialNumber}")
    public String delete(@PathVariable("serialNumber") long serialNumber){
        orderService.changeOrderStatus(OrderStatus.DELETED, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }

    @PostMapping("/restore/{serialNumber}")
    public String restore(@PathVariable("serialNumber") long serialNumber){
        orderService.changeOrderStatus(OrderStatus.ORDERED, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }
}
