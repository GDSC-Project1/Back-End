package wan.tigglemate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false ,unique=true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private String email;

    private String name;
}
