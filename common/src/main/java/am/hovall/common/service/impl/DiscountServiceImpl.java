package am.hovall.common.service.impl;


import am.hovall.common.entity.Discount;
import am.hovall.common.repository.DiscountRepository;
import am.hovall.common.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;


    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }
}
