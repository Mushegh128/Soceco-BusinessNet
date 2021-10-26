package am.hovall.common.repository;

import am.hovall.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


   List<User> findALlByCompany_Id(Long id);

   Optional<User> findByEmail(String email);
}
