package am.hovall.common.repositories;

import am.hovall.common.model.entities.MadeInCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MadeInCountryRepository extends JpaRepository<MadeInCountry, Long> {

    MadeInCountry findByTitle(String title);
}
