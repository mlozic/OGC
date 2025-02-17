package com.example.secret_store;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.secret_store.exception.ErrorResponse;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleDataAccessException(AccessDeniedException ex) {
    ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
  }
}
