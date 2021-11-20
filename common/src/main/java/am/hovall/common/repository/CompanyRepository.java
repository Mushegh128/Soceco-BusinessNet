package am.hovall.common.repository;

import am.hovall.common.entity.Company;
import am.hovall.common.request.CompanyRequest;
import am.hovall.common.response.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByBarcode(long barcode);

    Optional<Company> findByRegisterNumber(String regNumber);


    List<Company> findByOrderByNameDesc();

    List<Company> findByOrderByNameAsc();

    Optional<Company> findByName(String name);

    List<Company> findAllByPresSeller_Id(Long id);

}
