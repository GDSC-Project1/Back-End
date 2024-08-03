package server.tigglemate.domain.account.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.tigglemate.domain.account.application.AccountService;
import server.tigglemate.domain.account.domian.entity.Account;
import server.tigglemate.domain.account.dto.AccountDTO;
import server.tigglemate.domain.account.dto.CategoryCountDTO;
import server.tigglemate.global.template.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/account-book/account")
public class AccountApiController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "가계부 내역 입력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @PostMapping("/create")
    public ResponseTemplate<Account> create(@RequestBody AccountDTO accountDTO) {
        Account account = accountService.create(accountDTO);
        return new ResponseTemplate<>(HttpStatus.OK, "입력 성공", account);
    }

    @Operation(summary = "가계부 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/list")
    public ResponseTemplate<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAccountLists();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", accounts);
    }

    @Operation(summary = "금일 지출 내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/expense/today-list")
    public ResponseTemplate<List<Account>> getTodayExpenses() {
        List<Account> expenses = accountService.getTodayExpenses();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", expenses);
    }

    @Operation(summary = "금일 지출 금액 합계 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("/expense/today-sum")
    public ResponseTemplate<Integer> getTodayExpenseSum() {
        Integer sum = accountService.getSumOfExpensesOfToday();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @Operation(summary = "현월 지출 금액 합계 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/sum/this-month")
    public ResponseTemplate<Integer> getExpenseSumOfThisMonth() {
        Integer sum = accountService.getSumOfExpensesOfThisMonth();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @Operation(summary = "목표 금액 대비 현월 지출 금액 합계 비교 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/gap/by-target")
    public ResponseTemplate<Integer> getExpenseGap() {
        Integer gap = accountService.getGapBetweenExpensesOfThisMonthAndTargetAmount();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", gap);
    }

    @Operation(summary = "전월 대비 현월 지출 금액 합계 비교 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/gap/by-lastmonth")
    public ResponseTemplate<Integer> getExpenseGapLastMonth() {
        Integer gap = accountService.getGapBetweenThisMonthAndLastMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", gap);
    }

    @Operation(summary = "현월 카테고리별 지출 금액 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/sum/this-month/by-category")
    public ResponseTemplate<List<Object[]>> getExpenseSumOfThisMonthByCategory() {
        List<Object[]> sum = accountService.getSumOfExpensesByCategory();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @Operation(summary = "현월 만족도별 지출 금액 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/sum/this-month/by-satisfaction")
    public ResponseTemplate<List<Object[]>> getExpenseSumOfThisMonthBySatisfaction() {
        List<Object[]> sum = accountService.getSumOfExpensesBySatisfaction();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @Operation(summary = "현월 일별 지출 금액 합계 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/sum/this-month/by-day")
    public ResponseTemplate<List<AccountDTO>> getExpenseSumOfThisMonthByDay() {
        List<AccountDTO> sum = accountService.getDailySumOfExpensesForThisMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @Operation(summary = "전월 카테고리별 항목수 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패")
    })
    @GetMapping("expense/category/counts/by-lastmonth")
    public ResponseTemplate<List<CategoryCountDTO>> getExpenseCategoryCountByLastMonth() {
        List<CategoryCountDTO> result = accountService.getCategoryCountsByLastMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", result);
    }
}
