package server.tigglemate.domain.User.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import server.tigglemate.domain.accountBook.domain.entity.AccountBook;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nickname;

    @Column(nullable = false ,unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private String email;

    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinColumn(name = "accountbook")
    @JsonManagedReference
    private AccountBook accountBook;

}
