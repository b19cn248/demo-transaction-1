package com.opn.demo.exception.user;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.BadRequestException;

public class UserIsActiveException extends BadRequestException {

  public UserIsActiveException() {
    super(MessageExceptionConstant.USER_IS_ACTIVE);
  }
}
