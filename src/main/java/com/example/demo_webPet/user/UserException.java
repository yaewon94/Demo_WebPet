package com.example.demo_webPet.user;

import lombok.Getter;

@Getter
class UserException extends RuntimeException{

    private final UserCode code;

    UserException(UserCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
