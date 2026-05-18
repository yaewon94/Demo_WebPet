package com.example.demo_webPet.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signup(String id, String password) {
        User user = new User();
        user.setId(USER_ID_TYPE.of(id));
        user.setPassword(password);
        System.out.println("====================== 회원객체 = " + user.toString());

        userRepository.save(user);
    }
}