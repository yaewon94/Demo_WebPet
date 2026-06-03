package com.example.demo_webPet.common;

public record Response<T>(
        String code,
        String message,
        T data
) {}