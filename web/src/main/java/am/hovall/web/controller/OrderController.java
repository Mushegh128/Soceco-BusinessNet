package am.hovall.web.controller;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.response.PaymentResponse;
import am.hovall.common.response.ProductOrderResponse;
import am.hovall.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String orders(ModelMap modelMap) {
        modelMap.addAttribute("orderedOrders", orderService.findByStatus(OrderStatus.ORDERED));
        return "orders";
    }

    @GetMapping("/modify/")
    public String singleOrder(ModelMap modelMap, @RequestParam("serialNumber") long serialNumber) {
        modelMap.addAttribute("order", orderService.findBySerialNumber(serialNumber));
        return "singleOrder";
    }

    @PostMapping("/submit/{serialNumber}")
    public String submit(@PathVariable("serialNumber") long serialNumber) {
        orderService.changeOrderStatus(OrderStatus.SOLD_DEBT, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }

    @DeleteMapping("/{serialNumber}")
    public String delete(@PathVariable("serialNumber") long serialNumber) {
        orderService.changeOrderStatus(OrderStatus.DELETED, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }

    @PostMapping("/restore/{serialNumber}")
    public String restore(@PathVariable("serialNumber") long serialNumber) {
        orderService.changeOrderStatus(OrderStatus.ORDERED, serialNumber);
        return "redirect:/orders/" + serialNumber;
    }

    @GetMapping("/archive")
    public String archivedOrders(ModelMap modelMap) {
        modelMap.addAttribute("");
        return "archive";
    }

    @GetMapping("/removeOrderedProduct/{productOrderId}/{serialNumber}")
    public String removeOrderedProduct(@PathVariable("productOrderId") long productOrderId, @PathVariable("serialNumber") Long serialNumber) {
        orderService.deleteOrderedProductFromOrder(productOrderId, serialNumber);
        return "redirect:/orders/modify/?serialNumber=" + serialNumber;
    }
}
