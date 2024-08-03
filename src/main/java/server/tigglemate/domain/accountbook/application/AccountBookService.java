package server.tigglemate.domain.accountbook.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.tigglemate.domain.accountbook.domain.entity.AccountBook;
import server.tigglemate.domain.accountbook.domain.repository.AccountBookRepository;
import server.tigglemate.domain.accountbook.dto.AccountBookDTO;

import java.util.List;
import java.util.Optional;

@Service
public class AccountBookService {

    @Autowired
    private AccountBookRepository accountBookRepository;

    // 가계부 생성 - 목표 금액 설정
    public AccountBook create(AccountBookDTO accountBookDTO) {

        AccountBook accountBook = new AccountBook();

        accountBook.setTargetAmount(accountBookDTO.getTargetAmount());

        return accountBookRepository.save(accountBook);
    }

    // 가계부 조회
    public List<AccountBook> getAccountBooks() {
        return accountBookRepository.findAll();
    }
}
