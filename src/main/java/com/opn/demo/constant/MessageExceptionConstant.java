package com.opn.demo.constant;

import lombok.Getter;


@Getter
public class MessageExceptionConstant {

  public static final String FOREIGN_BETWEEN_ADDRESS_AND_USER_IS_NOT_EXIST = "One of couple is not exist";
  public static final String USER_OR_ACCOUNT_NUMBER_IS_NOT_EXIST = "Sending account number or receiving account number is unavailable";
  public static final String ACCOUNT_IS_NOT_ENOUGH = "Amount is not enough";
  public static final String USER_IS_NOT_EXIST = "User is not exist";
  public static final String FIELD_IN_USER_EDITION_IS_EMPTY = "One of information is empty";
  public static final String ACCOUNT_IS_NOT_FOUND = "account is not found";
  public static final String JWT_IS_NOT_VALID = "jwt is not valid";
  public static final String USER_IS_ACTIVE = "user is active";

  private MessageExceptionConstant(){}

}
