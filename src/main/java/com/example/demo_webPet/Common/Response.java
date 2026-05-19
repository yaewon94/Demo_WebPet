package com.example.demo_webPet.Common;

public record Response<T>(
        String code,
        String message,
        T data
) {}