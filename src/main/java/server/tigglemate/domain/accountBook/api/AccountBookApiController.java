package server.tigglemate.domain.accountBook.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.tigglemate.domain.accountBook.application.AccountBookService;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;
import server.tigglemate.domain.accountBook.dto.AccountBookDTO;
import server.tigglemate.global.template.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/account-book")
public class AccountBookApiController {

    @Autowired
    private AccountBookService accountBookService;


    @PostMapping("/create")
    public ResponseTemplate<AccountBook> create(@RequestBody AccountBookDTO accountBookDTO) {

        AccountBook accountBook = accountBookService.create(accountBookDTO);

        return new ResponseTemplate<>(HttpStatus.OK, "생성 성공", accountBook);
    }


    @GetMapping("")
    public ResponseTemplate<List<AccountBook>> getAll() {
        List<AccountBook> accountBook = accountBookService.getAccountBooks();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", accountBook);
    }
}