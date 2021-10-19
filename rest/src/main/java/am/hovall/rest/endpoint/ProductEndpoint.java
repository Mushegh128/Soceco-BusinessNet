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
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping("/products/category")
    public ResponseEntity<List<ProductResponse>> getAllByCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.ok(productService.findAllByCategory(productCategory));
    }

    @GetMapping("/products/brand")
    public ResponseEntity<List<ProductResponse>> getAllByBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(productService.findAllByBrand(brand));
    }

    @GetMapping("/products/byRange")
    public ResponseEntity<List<ProductResponse>> getAllByRange(@RequestBody Double startPrice, Double endPrice) {
        return ResponseEntity.ok(productService.findAllByPriceRange(startPrice, endPrice));
    }

    @PutMapping("/products/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.add(productRequest));
    }

    @PostMapping("/products/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.update(productRequest));
    }

    @DeleteMapping("/products/deactivate/{id}")
    public ResponseEntity deactivate(@PathVariable("id") Long id) {
        if (productService.deactivate(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
