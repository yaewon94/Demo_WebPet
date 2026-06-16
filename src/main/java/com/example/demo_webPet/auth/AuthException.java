package com.example.demo_webPet.auth;

import lombok.Getter;

@Getter
public final class AuthException extends RuntimeException {

    private final AuthCode code;
    private final String redirectionPage;

    AuthException(AuthCode code, String redirectionPage) {
        super(code.getMessage());
        this.code = code;
        this.redirectionPage = redirectionPage;
    }
}
