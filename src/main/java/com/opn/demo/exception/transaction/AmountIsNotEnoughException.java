package com.opn.demo.exception.transaction;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.BadRequestException;

public class AmountIsNotEnoughException extends BadRequestException {
  public AmountIsNotEnoughException() {
    super(MessageExceptionConstant.ACCOUNT_IS_NOT_ENOUGH);
  }
}
