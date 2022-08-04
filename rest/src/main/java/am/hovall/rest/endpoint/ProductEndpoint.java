package am.hovall.rest.endpoint;

import am.hovall.common.exception.ProductNotFoundException;
import am.hovall.common.request.ProductRequest;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/unSynchronized")
    public ResponseEntity<List<ProductResponse>> findAllUnSynchronized() {
        return ResponseEntity.ok(productService.findAllUnSynchronized());
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<List<ProductResponse>> getAllByBarcode(@PathVariable long barcode) {
        return ResponseEntity.ok(productService.getAllProductsByBarcode(barcode));
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getAllByCategory(@RequestParam long productCategoryId, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.findAllByCategoryId(productCategoryId, pageable));
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductResponse>> getAllByBrand(@RequestParam long id, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.findAllByBrandId(id, pageable));
    }

    @GetMapping("/byRange")
    public ResponseEntity<List<ProductResponse>> getAllByRange(@RequestParam double startPrice, double endPrice, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.findAllByPriceRange(startPrice, endPrice, pageable));

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

    @PostMapping("/uploadImage/{productId}")
    public ResponseEntity<?>
    uploadProductImage(@RequestParam("image") MultipartFile file,
                       @PathVariable("productId") long id) throws IOException {
        try {
            productService.saveImage(file, id);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/uploadImages")
    public ResponseEntity<?> uploadProductsImages(@RequestParam List<MultipartFile> images) {
        try {
            productService.saveProductsImages(images);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
