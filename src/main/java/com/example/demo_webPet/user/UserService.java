package com.example.demo_webPet.user;
import com.example.demo_webPet.common.UrlConstants;
import org.springframework.stereotype.Service;

@Service
class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LoginUserDto createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(dto.userId())){
            throw new UserException(UserCode.ERROR_DUPLICATED_USER_ID);
        }

        User user = new User();
        user.setUserId(dto.userId());
        user.setPassword(dto.password());
        userRepository.save(user);

        return new LoginUserDto(user.getId(), user.getUserId(), null);
    }

    void loginUser(LoginUserDto dto){
        // 아이디 존재 여부 체크
        User user = userRepository
                .findByUserId(dto.userId())
                .orElseThrow(() ->
                        new UserAuthException(UserCode.ERROR_USER_ID_IS_NOT_EXIST, UrlConstants.URL_LOGIN));

        // 비밀번호 일치 여부 체크
        if (!user.getPassword().equals(dto.password())) {
            throw new UserAuthException(UserCode.ERROR_USER_PASSWORD_MISMATCH, UrlConstants.URL_LOGIN);
        }
    }
}