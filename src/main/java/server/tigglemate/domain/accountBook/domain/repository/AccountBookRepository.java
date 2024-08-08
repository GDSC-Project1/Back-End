package server.tigglemate.domain.accountBook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    Optional<AccountBook> findById(int userId);

    @Query("SELECT a.targetAmount FROM AccountBook a")
    Integer getTargetAmount();
}
