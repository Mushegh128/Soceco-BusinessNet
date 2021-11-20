package am.hovall.common.service.impl;


import am.hovall.common.entity.CompanyType;
import am.hovall.common.repository.CompanyTypeRepository;
import am.hovall.common.service.CompanyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyServiceTypeImpl implements CompanyTypeService {
    private final CompanyTypeRepository companyTypeRepository;


    @Override
    public List<CompanyType> findAll() {
        return companyTypeRepository.findAll();
    }
}
