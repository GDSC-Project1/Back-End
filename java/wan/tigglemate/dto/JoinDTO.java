package wan.tigglemate.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String nickname;
    private String username; //email
    private String password;
    private String checkpassword;
}
