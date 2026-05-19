package com.example.demo_webPet.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserRequest dto) {
        // 아이디 중복 체크
        if (userRepository.existsByUserId(dto.userId())){
            throw new UserIdDuplicatedException();
        }

        User user = new User();
        user.setUserId(dto.userId());
        user.setPassword(dto.password());

        userRepository.save(user);
    }
}