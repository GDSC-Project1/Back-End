package server.tigglemate.domain.account.dto;

import lombok.Getter;
import lombok.Setter;
import server.tigglemate.domain.account.domain.entity.AccountCategory;
import server.tigglemate.domain.account.domain.entity.AccountSatisfaction;
import server.tigglemate.domain.account.domain.entity.AccountType;

import java.time.LocalDate;

@Getter
@Setter
public class AccountDTO {

    private AccountType type;

    private AccountCategory category;

    private String details;

    private int amount;

    private String memo;

    private AccountSatisfaction satisfaction;

    private LocalDate createDate;

}
