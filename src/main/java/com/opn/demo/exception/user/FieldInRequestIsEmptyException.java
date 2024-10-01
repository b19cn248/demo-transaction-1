package com.opn.demo.exception.user;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.BadRequestException;

public class FieldInRequestIsEmptyException extends BadRequestException {
  public FieldInRequestIsEmptyException() {
    super(MessageExceptionConstant.FIELD_IN_USER_EDITION_IS_EMPTY);
  }
}
