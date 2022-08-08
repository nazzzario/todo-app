package com.nkrasnovoronka.todoapp.exception.controller;

import com.nkrasnovoronka.todoapp.exception.ErrorMessage;
import com.nkrasnovoronka.todoapp.exception.RefreshTokenException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RefreshTokenControllerAdvice {

  @ExceptionHandler(value = ReflectiveOperationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage handleRefreshTokenException(RefreshTokenException ex, WebRequest request) {
    return new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));
  }
}

