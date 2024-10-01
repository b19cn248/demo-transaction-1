package com.opn.demo.exception.user;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.NotFoundException;

public class UserIsNotExistException extends NotFoundException {
  public UserIsNotExistException() {
    super(MessageExceptionConstant.USER_IS_NOT_EXIST);
  }
}
