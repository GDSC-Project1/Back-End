package server.tigglemate.domain.User.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.tigglemate.domain.User.domain.entity.UserEntity;
import server.tigglemate.domain.User.domain.repository.UserRepository;
import server.tigglemate.domain.User.dto.JoinDTO;

import java.util.Objects;

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
        String checkPassword = joinDTO.getCheckPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        // 패스워드와 확인 패스워드가 일치하는지 확인
        if (!password.equals(checkPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        UserEntity data = new UserEntity();
        data.setNickname(nickname);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
