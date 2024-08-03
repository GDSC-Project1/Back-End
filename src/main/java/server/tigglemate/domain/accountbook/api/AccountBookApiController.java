package server.tigglemate.domain.accountbook.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.tigglemate.domain.accountbook.application.AccountBookService;
import server.tigglemate.domain.accountbook.domain.entity.AccountBook;
import server.tigglemate.domain.accountbook.dto.AccountBookDTO;
import server.tigglemate.global.template.ResponseTemplate;


import java.util.List;

@RestController
@RequestMapping("/api/account-book")
public class AccountBookApiController {

    @Autowired
    private AccountBookService accountBookService;

    @Operation(summary = "목표 금액 설정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/create")
    public ResponseTemplate<AccountBook> create(@RequestBody AccountBookDTO accountBookDTO) {

        AccountBook accountBook = accountBookService.create(accountBookDTO);

        return new ResponseTemplate<>(HttpStatus.OK, "생성 성공", accountBook);
    }

    @Operation(summary = "가계부 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("")
    public ResponseTemplate<List<AccountBook>> getAll() {
        List<AccountBook> accountBook = accountBookService.getAccountBooks();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", accountBook);
    }
}
