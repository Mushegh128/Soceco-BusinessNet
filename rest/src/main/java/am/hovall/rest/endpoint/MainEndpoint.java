package am.hovall.rest.endpoint;

import am.hovall.common.dto.CompanyDto;
import am.hovall.common.dto.ProductDto;
import am.hovall.common.entity.Company;
import am.hovall.common.entity.Product;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.ProductService;
import am.hovall.rest.response.MainResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainEndpoint {

    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;


    @GetMapping("/main")
    public ResponseEntity<MainResponse> homePageData(@RequestBody CompanyDto companyDto){
        List<Product> allProducts = productService.getAllProducts();
        Double companyTotalDebt = orderService.getCompanyDebt(modelMapper.map(companyDto, Company.class));
        List<ProductDto> productDtoList = new LinkedList<>();
        for (Product product : allProducts) {
            productDtoList.add(modelMapper.map(product, ProductDto.class));
        }
        return ResponseEntity.ok(MainResponse.builder()
                .companyTotalDebt(companyTotalDebt)
                .productDtoList(productDtoList)
                .build());
    }

}
