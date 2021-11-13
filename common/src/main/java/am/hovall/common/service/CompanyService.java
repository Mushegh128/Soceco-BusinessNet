package am.hovall.common.service;

import am.hovall.common.response.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse findById(Long id);

    CompanyResponse findByRegisterNumber(long registerNumber);

    List<CompanyResponse> findAll();

    List<CompanyResponse> findAllByPresSellerId(Long id);
}
