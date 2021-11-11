package am.hovall.common.service.impl;

import am.hovall.common.response.MainResponse;
import am.hovall.common.service.MainService;
import am.hovall.common.service.OrderService;
import am.hovall.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final OrderService orderService;
    private final ProductService productService;

    @Override
    public MainResponse collectMainResponse(long companyId) {
        final Double companyDebt = orderService.getCompanyDebt(companyId);
        return MainResponse.builder()
                .companyTotalDebt(companyDebt)
                .productResponseList(productService.getAllProducts(Pageable.unpaged()))
                .build();
    }
}
