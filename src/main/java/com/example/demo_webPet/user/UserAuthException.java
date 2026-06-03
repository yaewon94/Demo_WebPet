package com.example.demo_webPet.user;

import lombok.Getter;

@Getter
public final class UserAuthException extends UserException {

    private final String redirectionPage;

    UserAuthException(UserCode code, String redirectionPage) {
        super(code);
        this.redirectionPage = redirectionPage;
    }
}
