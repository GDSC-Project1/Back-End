package server.tigglemate_pre.domain.account.domian.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.tigglemate_pre.domain.accountbook.domain.entity.AccountBook;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountCategory category;

    private String details;

    private int amount;

    private String memo;

    @Enumerated(EnumType.STRING)
    private AccountSatisfaction satisfaction;

    private LocalDate createDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime createTime;

    @ManyToOne
    @JoinColumn(name ="accountbook_id")
    @JsonBackReference
    private AccountBook accountBook;
}
