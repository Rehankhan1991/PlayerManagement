package com.example.game.GameManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PlayersNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PlayersNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(PlayersNotFoundException ex) {
    return ex.getMessage();
  }
}