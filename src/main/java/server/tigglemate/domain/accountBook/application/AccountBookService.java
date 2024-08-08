package server.tigglemate.domain.accountBook.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.tigglemate.domain.User.domain.entity.UserEntity;
import server.tigglemate.domain.User.domain.repository.UserRepository;
import server.tigglemate.domain.User.dto.CustomUserDetails;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;
import server.tigglemate.domain.accountBook.domain.repository.AccountBookRepository;
import server.tigglemate.domain.accountBook.dto.AccountBookDTO;

import java.util.List;

@Service
public class AccountBookService {

    @Autowired
    private AccountBookRepository accountBookRepository;

    @Autowired
    private UserRepository userRepository;

    // 가계부 생성 - 목표 금액 설정
    public AccountBook create(AccountBookDTO accountBookDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();


        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        AccountBook accountBook = new AccountBook();

        accountBook.setTargetAmount(accountBookDTO.getTargetAmount());

        accountBook.setUser(user);

        return accountBookRepository.save(accountBook);
    }

    // 가계부 조회
    public List<AccountBook> getAccountBooks() {
        return accountBookRepository.findAll();
    }
}