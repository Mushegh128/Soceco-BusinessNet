package am.hovall.rest.endpoint;

import am.hovall.common.entity.Brand;
import am.hovall.common.entity.Product;
import am.hovall.common.entity.ProductCategory;
import am.hovall.common.mapper.ProductMapper;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/products/category")
    public ResponseEntity<List<ProductResponse>> getAllByCategory(@RequestBody ProductCategory productCategory) {
        return ResponseEntity.ok(parseToProductResponseList(productService.findAllByCategory(productCategory)));
    }

    @GetMapping("/products/brand")
    public ResponseEntity<List<ProductResponse>> getAllByBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(parseToProductResponseList(productService.findAllByBrand(brand)));
    }

    @GetMapping("/products/byRange")
    public ResponseEntity<List<ProductResponse>> getAllByRange(@RequestBody Double startPrice, Double endPrice) {
        return ResponseEntity.ok(parseToProductResponseList(productService.findAllByPriceRange(startPrice, endPrice)));
    }

    @PutMapping("/products/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest) {
        Product product = productService.add(productMapper.toEntity(productRequest));
        if (product == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @PostMapping("/products/update")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest) {
        Product product = productService.update(productMapper.toEntity(productRequest));
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
        return ResponseEntity.ok(productMapper.toResponse(product));
    }

    @DeleteMapping("/products/deactivate/{id}")
    public ResponseEntity deactivate(@PathVariable("id") Long id) {
        if (productService.deactivate(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    private List<ProductResponse> parseToProductResponseList(List<Product> productList) {
        List<ProductResponse> productResponseList = new LinkedList<>();
        for (Product product : productList) {
            productResponseList.add(productMapper.toResponse(product));
        }
        return productResponseList;
    }

}
