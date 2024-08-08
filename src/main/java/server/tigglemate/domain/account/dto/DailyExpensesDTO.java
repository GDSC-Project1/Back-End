package server.tigglemate.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyExpensesDTO {

    private LocalDate date;

    private int amount;
}
