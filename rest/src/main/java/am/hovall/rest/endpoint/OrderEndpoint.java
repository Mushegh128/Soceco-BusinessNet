package am.hovall.rest.endpoint;

import am.hovall.common.entity.OrderStatus;
import am.hovall.common.exception.OrderNotFoundException;
import am.hovall.common.request.OrderRequest;
import am.hovall.common.response.OrderResponse;
import am.hovall.common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderEndpoint {

    private final OrderService orderService;


    @GetMapping("/{serialNumber}")
    public ResponseEntity<OrderResponse> findBySerialNumber(@PathVariable("serialNumber") long serialNumber) {
        return ResponseEntity.ok(orderService.findBySerialNumber(serialNumber));
    }

    @GetMapping("/unSynchronized")
    public ResponseEntity<List<OrderResponse>> findAllUnSynchronized() {
        return ResponseEntity.ok(orderService.findAllUnSynchronized());
    }

    @GetMapping("/orderStatus/{orderStatus}")
    public ResponseEntity<List<OrderResponse>> findAllByOrderStatus(@PathVariable("orderStatus") OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.findAllByOrderStatus(orderStatus));
    }

    @GetMapping("/{userId}/{companyId}")
    public ResponseEntity<List<OrderResponse>> findAllByUserIdAndCompanyId(@PathVariable("userId") long userId, @PathVariable("companyId") long companyId) {
        return ResponseEntity.ok(orderService.findAllByUserIdAndCompanyId(userId, companyId));
    }

    @GetMapping("/byDate/")
    public ResponseEntity<List<OrderResponse>> findAllByDateRange(@RequestParam(value = "startDate") String startDate,
                                                                  @RequestParam(value = "endDate") String endDate) {
        return ResponseEntity.ok(orderService.findAllByDateRange(startDate, endDate));
    }


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