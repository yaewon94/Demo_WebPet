package com.example.demo_webPet.auth;

import com.example.demo_webPet.user.User;
import com.example.demo_webPet.user.UserType;

public record LoginUserDto(
        Long id,
        String user_name,
        UserType user_type
) {
    static LoginUserDto from(User user){
        return new LoginUserDto(user.getId(), user.getUserName(), user.getType());
    }
}
