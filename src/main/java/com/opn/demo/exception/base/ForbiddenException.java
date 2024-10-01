package com.opn.demo.exception.base;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException{
  public ForbiddenException(String message) {
    super(message, HttpStatus.FORBIDDEN);
  }
}
