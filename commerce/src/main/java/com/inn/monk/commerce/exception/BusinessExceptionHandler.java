package com.inn.monk.commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler {

@ExceptionHandler(BusinessException.class)
public ResponseEntity<String> handleBusinessException(BusinessException ex) {
return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation failed: " + ex.getMessage());
}
}