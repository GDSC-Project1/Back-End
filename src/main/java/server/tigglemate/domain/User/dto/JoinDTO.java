package server.tigglemate.domain.User.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String nickname;
    private String username; //email
    private String password;
    private String checkPassword;
}
