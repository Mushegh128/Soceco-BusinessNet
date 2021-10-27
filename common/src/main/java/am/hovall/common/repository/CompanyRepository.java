package am.hovall.common.repository;

import am.hovall.common.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByBarcode(long barcode);

    Optional<Company> findByRegisterNumber(long regNumber);
}
