package am.hovall.common.service;

import am.hovall.common.entity.Company;

import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(Long id);

    Company findByRegisterNumber(long registerNumber);
}
