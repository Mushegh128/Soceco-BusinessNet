package am.hovall.rest.endpoints;

import am.hovall.common.model.dto.ProductDto;
import am.hovall.common.model.dto.ProductSaveDto;
import am.hovall.common.model.entities.Product;
import am.hovall.common.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductEndpoint {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public List<Product> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable("id") long id) {
        return productService.findById(id);
    }

    @PostMapping("/")
    public ProductDto saveProduct(@RequestBody ProductSaveDto productSaveDto) {
        Product save = productService.save(modelMapper.map(productSaveDto, Product.class));
        return modelMapper.map(save, ProductDto.class);
    }

    @GetMapping("/productCategory/{id}")
    public List<ProductDto> findAllByProductCategory(@PathVariable("id") long id) {
        List<Product> byProductCategory = productService.findAllByProductCategory(id);
        List<ProductDto> productDtos = new LinkedList<>();
        byProductCategory.forEach(product ->
                productDtos.add(modelMapper.map(product, ProductDto.class))
        );
        return productDtos;
    }

    @GetMapping("/brand/{id}")
    public List<ProductDto> findAllByBrand(@PathVariable("id") long id) {
        List<Product> allByBrand = productService.findAllByBrand(id);
        List<ProductDto> productDtos = new LinkedList<>();
        allByBrand.forEach(product ->
                productDtos.add(modelMapper.map(product, ProductDto.class))
        );
        return productDtos;
    }

}
