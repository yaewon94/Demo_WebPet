package com.example.demo_webPet.user;
import org.springframework.stereotype.Service;

@Service
class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginUserDto createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(dto.userId())){
            throw new UserException(UserCode.ERROR_DUPLICATED_USER_ID);
        }

        User user = new User();
        user.setUserId(dto.userId());
        user.setPassword(dto.password());
        userRepository.save(user);

        // ID값 확인
        /*if(user.getId() < 0) {
            // TODO : DB에서 user데이터 삭제
            throw new UserException(UserCode.ERROR_USER_ID_VALUE_OVERFLOW);
        }*/

        return new LoginUserDto(user.getId(), user.getUserId());
    }
}