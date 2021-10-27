package am.hovall.common.repository;

import am.hovall.common.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt,Long> {
}
