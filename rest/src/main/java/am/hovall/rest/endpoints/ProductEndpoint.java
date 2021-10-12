package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.ProductCreateDto;
import am.hovall.common.model.dto.ProductDto;
import am.hovall.common.model.dto.ProductUpdateDto;
import am.hovall.common.model.entities.Brand;
import am.hovall.common.model.entities.Product;
import am.hovall.common.model.entities.ProductCategory;
import am.hovall.common.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductEndpoint {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping("/products/category")
    public ResponseEntity<List<ProductDto>> getAllByCategory(@RequestBody ProductCategory productCategory){
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByCategory(productCategory)));
    }

    @GetMapping("/products/brand")
    public ResponseEntity<List<ProductDto>> getAllByBrand(@RequestBody Brand brand){
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByBrand(brand)));
    }

    @GetMapping("/products/byRange")
    public ResponseEntity<List<ProductDto>> getAllByRange(@RequestBody Double startPrice, Double endPrice){
        return ResponseEntity.ok(parseToProductDtoList(productService.findAllByPriceRange(startPrice, endPrice)));
    }

    @PutMapping("/products/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductCreateDto productCreateDto){
        Product product = productService.add(modelMapper.map(productCreateDto, Product.class));
        if (product == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(modelMapper.map(product, ProductDto.class));
    }

    @PostMapping("/products/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductUpdateDto productUpdateDto){
        Product product = productService.update(modelMapper.map(productUpdateDto, Product.class));
        if (product == null){
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).build();
        }
        return ResponseEntity.ok(modelMapper.map(product, ProductDto.class));
    }

    @DeleteMapping("/products/deactivate/{id}")
    public ResponseEntity deactivate(@PathVariable("id") Long id){
        if (productService.deactivate(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }


    private List<ProductDto> parseToProductDtoList(List<Product> productList){
        List<ProductDto> productDtoList = new LinkedList<>();
        for (Product product : productList) {
            productDtoList.add(modelMapper.map(product, ProductDto.class));
        }
        return productDtoList;
    }

}
