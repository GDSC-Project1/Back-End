package server.tigglemate.domain.account.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import server.tigglemate.domain.User.application.CustomUserDetailsService;
import server.tigglemate.domain.User.domain.entity.UserEntity;
import server.tigglemate.domain.User.domain.repository.UserRepository;
import server.tigglemate.domain.account.domain.entity.Account;
import server.tigglemate.domain.account.domain.entity.AccountCategory;
import server.tigglemate.domain.account.domain.repository.AccountRepository;
import server.tigglemate.domain.account.dto.AccountDTO;
import server.tigglemate.domain.account.dto.CategoryCountDTO;
import server.tigglemate.domain.account.dto.DailyExpensesDTO;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;
import server.tigglemate.domain.accountBook.domain.repository.AccountBookRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountBookRepository accountBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 가계부 내역 입력
    public Account create(AccountDTO accountDTO) {

        Long userId = customUserDetailsService.getUserId();

        AccountBook accountBook = accountBookRepository.findAccountBookById(userId);

        Account account = new Account();
        account.setType(accountDTO.getType());
        account.setCategory(accountDTO.getCategory());
        account.setDetails(accountDTO.getDetails());
        account.setAmount(accountDTO.getAmount());
        account.setMemo(accountDTO.getMemo());
        account.setSatisfaction(accountDTO.getSatisfaction());
        account.setCreateDate(accountDTO.getCreateDate()); // 날짜는 본인이 입력할 수 있도록
        account.setCreateTime(LocalTime.now()); // 생성시간은 생성시 시간으로 자동 설정
        account.setAccountBook(accountBook);

        return accountRepository.save(account);
    }

    // 가계부 내역 수정
    public Account update(AccountDTO accountDTO) {
        Account account = accountRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("Account not found"));

        List<AccountDTO> list = new ArrayList<>();
        list.add(accountDTO);

        for (AccountDTO dto : list) {
            if(dto.getType() != null) account.setType(dto.getType());
            if(dto.getCategory() != null) account.setCategory(dto.getCategory());
            if(dto.getDetails() != null) account.setDetails(dto.getDetails());
            if(dto.getAmount() != 0) account.setAmount(dto.getAmount());
            if(dto.getMemo() != null) account.setMemo(dto.getMemo());
            if(dto.getCreateDate() != null) account.setCreateDate(dto.getCreateDate());
        }

        return accountRepository.save(account);
    }

    // 가계부 내역 전체 조회
    public List<Account> getAccountLists() {

        Long userId = customUserDetailsService.getUserId();

        AccountBook accountBook = accountBookRepository.findById(userId).orElse(null);

        List<Account> expenses = Objects.requireNonNull(accountBook).getAccounts();

        expenses.sort(Comparator.comparing(Account::getCreateDate)
                .thenComparing(Account::getCreateTime)
                .reversed());

        return expenses;
    }

    // 금일 소비 내역 리스트 조회
    public List<Account> getTodayExpenses() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();
        List<Account> expenses = accountRepository.findAllByCreateDate(today, userId);

        expenses.sort(Comparator.comparing(Account::getCreateTime)
                .reversed());

        return expenses;
    }

    // 금일 지출 합계 조회
    public Integer getSumOfExpensesOfToday() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();

        return accountRepository.sumTodayExpenses(today, userId);
    }

    // 현월 지출 합계 조회
    public Integer getSumOfExpensesOfThisMonth() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.sumExpensesByMonth(year, month, userId);
    }

    // 전월 대비 현월 지출 금액 차이 조회
    public Integer getGapBetweenThisMonthAndLastMonth() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        Integer lastMonth = accountRepository.sumExpensesByMonth(year, month - 1, userId);
        Integer thisMonth = accountRepository.sumExpensesByMonth(year, month, userId);

        if (lastMonth == null) {
            lastMonth = 0;
        }

        return lastMonth - thisMonth;
    }

    // 목표 금액 대비 현월 소비 금액 차이 조회
    public Integer getGapBetweenExpensesOfThisMonthAndTargetAmount() {

        Long userId = customUserDetailsService.getUserId();

        Integer sumOfExpensesOfThisMonth = getSumOfExpensesOfThisMonth();
        Integer targetAmount = accountBookRepository.getTargetAmount(userId);

        return targetAmount - sumOfExpensesOfThisMonth;
    }

    // 현월 카테고리별 지출 금액 합계 조회
    public List<Object[]> getSumOfExpensesByCategory() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.getExpensesByCategory(year, month, userId);
    }

    // 현월 만족도별 지출 금액 합계 조회
    public List<Object[]> getSumOfExpensesBySatisfaction() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.getExpensesBySatisfaction(year, month, userId);
    }

    // 현월 일별 소비 금액 합계 조회
    public List<DailyExpensesDTO> getDailySumOfExpensesForThisMonth() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        List<Object[]> results = accountRepository.sumExpensesByDayForMonth(year, month, userId);

        List<DailyExpensesDTO> dailyExpenses = new ArrayList<>();
        for(Object[] objects : results) {
            LocalDate date = (LocalDate)objects[0];
            Long sum = (Long)objects[1];
            DailyExpensesDTO dto = new DailyExpensesDTO();
            dto.setDate(date);
            dto.setAmount(sum.intValue());
            dailyExpenses.add(dto);
        }

        return dailyExpenses;
    }

    // 전월 카테고리별 항목수 개수가 많은 순으로 리스트 조회
    public List<CategoryCountDTO> getCategoryCountsByLastMonth() {

        Long userId = customUserDetailsService.getUserId();

        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        List<Object[]> results = accountRepository.countExpensesByCategoryForMonth(year, month - 1, userId);

        List<CategoryCountDTO> categoryCounts = new ArrayList<>();
        for(Object[] objects : results) {
            AccountCategory category = (AccountCategory)objects[0];
            Long count = (Long)objects[1];
            CategoryCountDTO dto = new CategoryCountDTO();
            dto.setCategory(category);
            dto.setCount(count.intValue());
            categoryCounts.add(dto);
        }

        categoryCounts.sort(Comparator.comparing(CategoryCountDTO::getCount)
                .reversed());

        return categoryCounts;
    }


}
