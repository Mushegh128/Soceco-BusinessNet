package am.hovall.common.services.impl;

import am.hovall.common.model.entities.Company;
import am.hovall.common.repositories.CompanyRepository;
import am.hovall.common.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Optional<Company> findById(Long id) {
        return Optional.empty();
    }
}
