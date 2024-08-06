package server.tigglemate_pre.domain.accountbook.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.tigglemate_pre.domain.accountbook.domain.entity.AccountBook;
import server.tigglemate_pre.domain.accountbook.domain.repository.AccountBookRepository;
import server.tigglemate_pre.domain.accountbook.dto.AccountBookDTO;

import java.util.List;

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
