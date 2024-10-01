package com.opn.demo.exception.jwt;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.ForbiddenException;

public class JwtIsNotValidException extends ForbiddenException {
  public JwtIsNotValidException() {
    super(MessageExceptionConstant.JWT_IS_NOT_VALID);
  }
}
