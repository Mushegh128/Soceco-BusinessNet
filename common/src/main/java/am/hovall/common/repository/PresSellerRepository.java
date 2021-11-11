package am.hovall.common.repository;

import am.hovall.common.entity.PresSeller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PresSellerRepository extends JpaRepository<PresSeller,Long> {

    Optional<PresSeller> findByEmail(String email);

    Optional<PresSeller> findByName(String name);

    Optional<PresSeller> findByPhoneNumber(String phoneNumber);
}
