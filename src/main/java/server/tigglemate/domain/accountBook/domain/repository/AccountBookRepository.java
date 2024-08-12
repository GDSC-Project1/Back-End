package server.tigglemate.domain.accountBook.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    @Query("SELECT a FROM AccountBook a WHERE a.id = :id")
    AccountBook findAccountBookById(Long id);

    @Query("SELECT a.targetAmount FROM AccountBook a WHERE a.id =:id")
    Integer getTargetAmount(Long id);
}
