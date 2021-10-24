package am.hovall.rest.endpoint;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping("/{page}")
    public ResponseEntity<List<ProductResponse>> findAll(@PathVariable int page) {
        return ResponseEntity.ok(productService.getAllProducts(page));
    }

    @PostMapping("/category/{page}")
    public ResponseEntity<List<ProductResponse>> getAllByCategory(@RequestBody ProductCategory productCategory, @PathVariable int page) {
        return ResponseEntity.ok(productService.findAllByCategoryId(productCategory.getId(), page));
    }

    @PostMapping("/brand{page}")
    public ResponseEntity<List<ProductResponse>> getAllByBrand(@RequestBody Brand brand, @PathVariable int page) {
        return ResponseEntity.ok(productService.findAllByBrandId(brand.getId(), page));
    }

    @GetMapping("/byRange")
    public ResponseEntity<List<ProductResponse>> getAllByRange(@RequestParam double startPrice, double endPrice) {
        return ResponseEntity.ok(productService.findAllByPriceRange(startPrice, endPrice));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.add(productRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest) {
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
