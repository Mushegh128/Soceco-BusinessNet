package am.hovall.rest.service.impl;

import am.hovall.common.entity.Product;
import am.hovall.common.mapper.ProductMapper;
import am.hovall.common.response.ProductResponse;
import am.hovall.common.service.CompanyService;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.ProductService;
import am.hovall.rest.response.MainResponse;
import am.hovall.rest.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductMapper productMapper;
    private final CompanyService companyService;

    @Override
    public MainResponse collectMainResponse(long registerNumber) {
        List<Product> allProducts = productService.getAllProducts();
        Double companyTotalDebt = orderService.getCompanyDebt(companyService.findByRegisterNumber(registerNumber));
        List<ProductResponse> productResponseList = new LinkedList<>();
        for (Product product : allProducts) {
            productResponseList.add(productMapper.toResponse(product));
        }
        return MainResponse.builder()
                .companyTotalDebt(companyTotalDebt)
                .productResponseList(productResponseList)
                .build();
    }
}
