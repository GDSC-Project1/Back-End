package server.tigglemate.domain.accountBook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    @Query("SELECT a.targetAmount FROM AccountBook a")
    Integer getTargetAmount();

}
