package server.tigglemate.domain.account.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.tigglemate.domain.account.application.AccountService;
import server.tigglemate.domain.account.domain.entity.Account;
import server.tigglemate.domain.account.dto.AccountDTO;
import server.tigglemate.domain.account.dto.CategoryCountDTO;
import server.tigglemate.domain.account.dto.DailyExpensesDTO;
import server.tigglemate.global.template.ResponseTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/account-book/account")
public class AccountApiController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/create")
    public ResponseTemplate<Account> create(@RequestBody AccountDTO accountDTO) {
        Account account = accountService.create(accountDTO);
        return new ResponseTemplate<>(HttpStatus.OK, "입력 성공", account);
    }


    @GetMapping("/list")
    public ResponseTemplate<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAccountLists();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", accounts);
    }


    @GetMapping("/expense/today-list")
    public ResponseTemplate<List<Account>> getTodayExpenses() {
        List<Account> expenses = accountService.getTodayExpenses();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", expenses);
    }

    @GetMapping("/expense/today-sum")
    public ResponseTemplate<Integer> getTodayExpenseSum() {
        Integer sum = accountService.getSumOfExpensesOfToday();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @GetMapping("expense/sum/this-month")
    public ResponseTemplate<Integer> getExpenseSumOfThisMonth() {
        Integer sum = accountService.getSumOfExpensesOfThisMonth();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @GetMapping("expense/gap/by-target")
    public ResponseTemplate<Integer> getExpenseGap() {
        Integer gap = accountService.getGapBetweenExpensesOfThisMonthAndTargetAmount();
        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", gap);
    }

    @GetMapping("expense/gap/by-lastmonth")
    public ResponseTemplate<Integer> getExpenseGapLastMonth() {
        Integer gap = accountService.getGapBetweenThisMonthAndLastMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", gap);
    }

    @GetMapping("expense/sum/this-month/by-category")
    public ResponseTemplate<List<Object[]>> getExpenseSumOfThisMonthByCategory() {
        List<Object[]> sum = accountService.getSumOfExpensesByCategory();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @GetMapping("expense/sum/this-month/by-satisfaction")
    public ResponseTemplate<List<Object[]>> getExpenseSumOfThisMonthBySatisfaction() {
        List<Object[]> sum = accountService.getSumOfExpensesBySatisfaction();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }


    @GetMapping("expense/sum/this-month/by-day")
    public ResponseTemplate<List<DailyExpensesDTO>> getExpenseSumOfThisMonthByDay() {
        List<DailyExpensesDTO> sum = accountService.getDailySumOfExpensesForThisMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", sum);
    }

    @GetMapping("expense/category/counts/by-lastmonth")
    public ResponseTemplate<List<CategoryCountDTO>> getExpenseCategoryCountByLastMonth() {
        List<CategoryCountDTO> result = accountService.getCategoryCountsByLastMonth();

        return new ResponseTemplate<>(HttpStatus.OK, "조회 성공", result);
    }
}