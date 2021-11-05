package am.hovall.rest.endpoint;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderEndpoint {

    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderResponse>> getOrdersByCompany(@RequestBody long companyId) {
        return ResponseEntity.ok(orderService.findAllByCompanyId(companyId));
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.save(orderRequest));
    }

    @PutMapping("/")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.updateOrder(orderRequest));
    }

    @PostMapping("/sold")
    public ResponseEntity<Boolean> sold(@RequestBody long serialNumber) {
        return ResponseEntity.ok(orderService.changeOrderStatus(OrderStatus.SOLD_DEBT, serialNumber));
    }

    @DeleteMapping("/")
    public ResponseEntity<Boolean> changeOrderStatusToDeleted(@RequestBody long serialNumber) {
        return ResponseEntity.ok(orderService.changeOrderStatus(OrderStatus.DELETED, serialNumber));
    }
}