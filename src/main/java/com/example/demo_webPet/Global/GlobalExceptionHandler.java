package com.example.demo_webPet.Global;

import com.example.demo_webPet.user.UserIdDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdDuplicatedException.class)
    public ResponseEntity<ErrorResponseDto> handle(UserIdDuplicatedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDto(e.getErrorCode().getCode(), e.getMessage()));
    }
}
