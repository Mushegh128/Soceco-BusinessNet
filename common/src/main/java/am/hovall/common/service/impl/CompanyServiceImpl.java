package am.hovall.common.service.impl;

import am.hovall.common.entity.Company;
import am.hovall.common.exception.CompanyNotFoundException;
import am.hovall.common.mapper.CompanyMapper;
import am.hovall.common.repository.CompanyRepository;
import am.hovall.common.response.CompanyResponse;
import am.hovall.common.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;


    @Override
    public CompanyResponse findById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        return companyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse findByRegisterNumber(long registerNumber) {
        Company company = companyRepository.findByRegisterNumber(registerNumber).orElseThrow(CompanyNotFoundException::new);
        return companyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> findAll() {
        List<Company> companyList = companyRepository.findAll();
        List<CompanyResponse> companyResponses
                = companyList.stream().map(companyMapper::toResponse).collect(Collectors.toList());
        return companyResponses;
    }

    @Override
    public List<CompanyResponse> findAllByPresSellerId(Long id) {
        List<Company> companyList = companyRepository.findAllByPresSeller_Id(id);
        return companyList.stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }
}
