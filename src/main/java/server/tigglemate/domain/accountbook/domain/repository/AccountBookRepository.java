package server.tigglemate.domain.accountbook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.tigglemate.domain.accountbook.domain.entity.AccountBook;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    @Query("SELECT a.targetAmount FROM AccountBook a")
    Integer getTargetAmount();
}
