package com.opn.demo.exception.user;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.exception.base.BadRequestException;

public class ForeignBetweenUserAndAddressExceptionIsNotExistException extends BadRequestException {
  public ForeignBetweenUserAndAddressExceptionIsNotExistException() {
    super(MessageExceptionConstant.FOREIGN_BETWEEN_ADDRESS_AND_USER_IS_NOT_EXIST);
  }
}
