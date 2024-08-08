package server.tigglemate.domain.accountBook.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.tigglemate.domain.User.domain.entity.UserEntity;
import server.tigglemate.domain.account.domain.entity.Account;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int targetAmount;

    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;
}