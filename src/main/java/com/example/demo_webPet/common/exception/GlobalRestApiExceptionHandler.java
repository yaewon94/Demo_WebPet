package com.example.demo_webPet.common.exception;

import com.example.demo_webPet.common.upload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestApiExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> handle(FileUploadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // service에서 dto validation 오류 예외를 던졌을 경우 호출
    @ExceptionHandler(CustomNotValidException.class)
    public ResponseEntity<?> handle(CustomNotValidException e){
        return ResponseEntity.badRequest().body(e.getErrors());
    }

    // dto validation 오류났을 경우 spring이 MethodArgumentNotValidException 던짐
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
