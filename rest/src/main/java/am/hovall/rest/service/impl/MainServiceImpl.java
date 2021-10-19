package am.hovall.rest.service.impl;

import am.hovall.common.service.CompanyService;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.ProductService;
import am.hovall.rest.response.MainResponse;
import am.hovall.rest.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final ProductService productService;
    private final OrderService orderService;
    private final CompanyService companyService;

    @Override
    public MainResponse collectMainResponse(long registerNumber) {
        Double companyTotalDebt = orderService.getCompanyDebt(companyService.findByRegisterNumber(registerNumber));
        return MainResponse.builder()
                .companyTotalDebt(companyTotalDebt)
                .productResponseList(productService.getAllProducts())
                .build();
    }
}
