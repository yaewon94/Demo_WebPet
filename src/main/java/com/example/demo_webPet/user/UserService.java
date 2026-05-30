package com.example.demo_webPet.user;

import com.example.demo_webPet.user.CreateUserRequest;
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
            throw new UserIdDuplicatedException();
        }

        User user = new User();
        user.setUserId(dto.userId());
        user.setPassword(dto.password());
        userRepository.save(user);

        return new LoginUserDto(user.getId(), user.getUserId(), user.getType());
    }
}