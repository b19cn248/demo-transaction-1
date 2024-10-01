package com.opn.demo.exception.transaction;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.BadRequestException;

public class AccountIsNotExistedException extends BadRequestException {
  public AccountIsNotExistedException() {
    super(MessageExceptionConstant.USER_OR_ACCOUNT_NUMBER_IS_NOT_EXIST);
  }
}
