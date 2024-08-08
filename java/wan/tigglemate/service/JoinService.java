package wan.tigglemate.service;

import wan.tigglemate.dto.JoinDTO;
import wan.tigglemate.entity.UserEntity;
import wan.tigglemate.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String nickname = joinDTO.getNickname();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String checkpassword = joinDTO.getCheckpassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        // 패스워드와 확인 패스워드가 일치하는지 확인
        if (!password.equals(checkpassword)) {
            return;
        }

        UserEntity data = new UserEntity();
        data.setNickname(nickname);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
