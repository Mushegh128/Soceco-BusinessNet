package am.hovall.common.service;

import am.hovall.common.request.CompanyRequest;
import am.hovall.common.response.CompanyResponse;

public interface CompanyService {

    CompanyResponse findById(Long id);

    CompanyResponse findByRegisterNumber(long registerNumber);
}
