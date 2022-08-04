package am.hovall.common.service;

import am.hovall.common.entity.Company;
import am.hovall.common.exception.CompanyNotFoundException;
import am.hovall.common.request.CompanyRequest;
import am.hovall.common.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse findById(Long id);

    List<CompanyResponse> findByOrderByNameDesc();

    List<CompanyResponse> findByOrderByNameAsc();

    CompanyResponse findByName(String name);

    void deactivatePresSeller(CompanyRequest companyRequest);

    void saveCompany(CompanyRequest companyRequest);

    void update(CompanyRequest companyRequest) throws CompanyNotFoundException;

    CompanyResponse findByRegisterNumber(String registerNumber);

    List<CompanyResponse> findAll();

    List<CompanyResponse> findAllByPresSellerId(Long id);
}
