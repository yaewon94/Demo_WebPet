package com.example.demo_webPet.user;

record LoginUserDto (
        Long id,
        String userId,
        User.UserType type){
    static final int MAX_LOGIN_TIME = 30 * 60;
}
