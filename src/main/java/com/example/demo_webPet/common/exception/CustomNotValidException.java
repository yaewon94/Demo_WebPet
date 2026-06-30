package com.example.demo_webPet.common.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public final class CustomNotValidException extends RuntimeException{

    private final Map<String, String> errors;

    public CustomNotValidException(Map<String, String> errors){
        if(errors.isEmpty()) throw new IllegalArgumentException("CustomNotValidException : [constructor parameter] errors(Map<String, String>) is empty");
        this.errors = errors;
    }
}
