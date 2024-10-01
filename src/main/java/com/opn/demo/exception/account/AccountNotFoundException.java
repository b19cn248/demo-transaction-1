package com.opn.demo.exception.account;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
  public AccountNotFoundException() {
    super(MessageExceptionConstant.ACCOUNT_IS_NOT_FOUND);
  }
}
