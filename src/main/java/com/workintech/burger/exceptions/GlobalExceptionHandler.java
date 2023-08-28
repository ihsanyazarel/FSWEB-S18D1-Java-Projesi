package com.workintech.burger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity handleMappingException(MethodArgumentNotValidException exception){
        List list = exception.getBindingResult()
                .getFieldErrors().stream().map(fieldError -> {
                    Map<String, String> newMap = new HashMap<>();
                    newMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return newMap;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(list);
    }

    public ResponseEntity<BurgerErrorResponse> handleException(Exception exception){
        BurgerErrorResponse response = new BurgerErrorResponse(HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
