package am.hovall.common.repository;

import am.hovall.common.entity.MadeInCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MadeInCountryRepository extends JpaRepository<MadeInCountry, Long> {

    MadeInCountry findByTitle(String title);
}
