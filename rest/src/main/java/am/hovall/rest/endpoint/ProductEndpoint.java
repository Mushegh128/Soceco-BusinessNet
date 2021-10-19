package am.hovall.rest.endpoint;

import am.hovall.common.dto.ProductCreateDto;
import am.hovall.common.dto.ProductDto;
import am.hovall.common.dto.ProductUpdateDto;
import am.hovall.common.entity.Product;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductEndpoint {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(parseToProductDtoList(productService.getAllProducts()));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getAllByCategory(@PathVariable long id) {
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByCategoryId(id)));
    }

    @GetMapping("/brand{id}")
    public ResponseEntity<List<ProductDto>> getAllByBrand(@PathVariable long id) {
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByBrandId(id)));
    }

    @GetMapping("/byRange")
    public ResponseEntity<List<ProductDto>> getAllByRange(@RequestParam double startPrice, double endPrice) {
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByPriceRange(startPrice, endPrice)));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductCreateDto productCreateDto) {
        Product product = productService.add(modelMapper.map(productCreateDto, Product.class));
        if (product == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(modelMapper.map(product, ProductDto.class));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductUpdateDto productUpdateDto) {
        Product product = productService.update(modelMapper.map(productUpdateDto, Product.class));
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
        return ResponseEntity.ok(modelMapper.map(product, ProductDto.class));
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable("id") Long id) {
        if (productService.deactivate(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    private List<ProductDto> parseToProductDtoList(List<Product> productList) {
        List<ProductDto> productDtoList = new LinkedList<>();
        for (Product product : productList) {
            productDtoList.add(modelMapper.map(product, ProductDto.class));
        }
        return productDtoList;
    }

}
