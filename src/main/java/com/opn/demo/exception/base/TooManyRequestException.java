package com.opn.demo.exception.base;

import org.springframework.http.HttpStatus;

public class TooManyRequestException extends BaseException{

  public TooManyRequestException(String message) {
    super(message, HttpStatus.TOO_MANY_REQUESTS);
  }
}
