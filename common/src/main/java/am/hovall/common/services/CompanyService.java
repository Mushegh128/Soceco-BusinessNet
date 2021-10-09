package am.hovall.common.services;

import am.hovall.common.model.entities.Company;

import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(Long id);

}
