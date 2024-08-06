package server.tigglemate.domain.account.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.tigglemate.domain.account.domian.entity.Account;
import server.tigglemate.domain.account.domian.entity.AccountCategory;
import server.tigglemate.domain.account.domian.entity.AccountType;
import server.tigglemate.domain.account.domian.repository.AccountRepository;
import server.tigglemate.domain.account.dto.AccountDTO;
import server.tigglemate.domain.account.dto.CategoryCountDTO;
import server.tigglemate.domain.account.dto.DailyExpensesDTO;
import server.tigglemate.domain.accountbook.domain.entity.AccountBook;
import server.tigglemate.domain.accountbook.domain.repository.AccountBookRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountBookRepository accountBookRepository;

    // 가계부 내역 입력
    public Account create(AccountDTO accountDTO) {

        // 가계부는 유저당 1개만 생성 가능
        AccountBook accountBook = accountBookRepository.findById(1L).orElseThrow(() -> new NoSuchElementException("Account book not found"));

        Account account = new Account();
        account.setType(accountDTO.getType());
//        if (accountDTO.getType() != AccountType.INCOME) { // 수입 내역이면 카테고리 값과 만족도 값을 받지 않도록.
//            account.setCategory(accountDTO.getCategory());
//            account.setSatisfaction(accountDTO.getSatisfaction());
//        }
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

        List<Account> expenses = accountRepository.findAll();

        expenses.sort(Comparator.comparing(Account::getCreateDate)
                .thenComparing(Account::getCreateTime)
                .reversed());

        return expenses;
    }

    // 금일 소비 내역 리스트 조회
    public List<Account> getTodayExpenses() {
        LocalDate today = LocalDate.now();
        List<Account> expenses = accountRepository.findAllByCreateDate(today);

        expenses.sort(Comparator.comparing(Account::getCreateTime)
                .reversed());

        return expenses;
    }

    // 금일 지출 합계 조회
    public Integer getSumOfExpensesOfToday() {
        LocalDate today = LocalDate.now();

        return accountRepository.sumTodayExpenses(today);
    }

    // 현월 지출 합계 조회
    public Integer getSumOfExpensesOfThisMonth() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.sumExpensesByMonth(year, month);
    }

    // 전월 대비 현월 지출 금액 차이 조회
    public Integer getGapBetweenThisMonthAndLastMonth() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        Integer lastMonth = accountRepository.sumExpensesByMonth(year, month - 1);
        Integer thisMonth = accountRepository.sumExpensesByMonth(year, month);

        if (lastMonth == null) {
            lastMonth = 0;
        }

        return lastMonth - thisMonth;
    }

    // 목표 금액 대비 현월 소비 금액 차이 조회
    public Integer getGapBetweenExpensesOfThisMonthAndTargetAmount() {
        Integer sumOfExpensesOfThisMonth = getSumOfExpensesOfThisMonth();
        Integer targetAmount = accountBookRepository.getTargetAmount();

        return targetAmount - sumOfExpensesOfThisMonth;
    }

    // 현월 카테고리별 지출 금액 합계 조회
    public List<Object[]> getSumOfExpensesByCategory() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.getExpensesByCategory(year, month);
    }

    // 현월 만족도별 지출 금액 합계 조회
    public List<Object[]> getSumOfExpensesBySatisfaction() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return accountRepository.getExpensesBySatisfaction(year, month);
    }

    // 현월 일별 소비 금액 합계 조회
    public List<DailyExpensesDTO> getDailySumOfExpensesForThisMonth() {
        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        List<Object[]> results = accountRepository.sumExpensesByDayForMonth(year, month);

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
        LocalDate today = LocalDate.now();

        int year = today.getYear();
        int month = today.getMonthValue();

        List<Object[]> results = accountRepository.countExpensesByCategoryForMonth(year, month - 1);

        List<CategoryCountDTO> categoryCounts = new ArrayList<>();
        for(Object[] objects : results) {
            AccountCategory category = (AccountCategory)objects[0];
            Long count = (Long)objects[1];
            CategoryCountDTO dto = new CategoryCountDTO();
            dto.setCategory(category);
            dto.setCount(count.intValue());
            categoryCounts.add(dto);
        }
        return categoryCounts;
    }
}
