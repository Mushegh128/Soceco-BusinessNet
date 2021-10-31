package am.hovall.rest.endpoint;

import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductResponse>> getAllByCategory(@PathVariable long id) {
        return ResponseEntity.ok(productService.findAllByCategoryId(id));
    }

    @GetMapping("/brand{id}")
    public ResponseEntity<List<ProductResponse>> getAllByBrand(@PathVariable long id) {
        return ResponseEntity.ok(productService.findAllByBrandId(id));
    }

    @GetMapping("/byRange")
    public ResponseEntity<List<ProductResponse>> getAllByRange(
            @RequestParam @DecimalMin(value = "0.0") double startPrice, @DecimalMin("0.0") double endPrice) {
        return ResponseEntity.ok(productService.findAllByPriceRange(startPrice, endPrice));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.add(productRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(productRequest));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable("id") Long id) {
        if (productService.deactivate(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
