package com.opn.demo.facade.impl;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.dto.request.TransferDTO;
import com.opn.demo.dto.response.TransactionDTO;
import com.opn.demo.entity.User;
import com.opn.demo.exception.base.BadRequestException;
import com.opn.demo.exception.transaction.AccountIsNotExistedException;
import com.opn.demo.exception.transaction.AmountIsNotEnoughException;
import com.opn.demo.facade.TransactionMoneyFacade;
import com.opn.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TransactionMoneyFacadeImpl implements TransactionMoneyFacade {

  private final UserService userService;

  @Override
  @Transactional
  public TransactionDTO transfer(int id, TransferDTO transferDTO) {

    log.info("(tranfer) id:{},transferDTO:{}", id, transferDTO);
    User user = userService.findUserById(id);

    this.checkUserAndAccountTransferIsExist(user,transferDTO);

    this.checkMoneyInAccountIsEnough(user,transferDTO);


    userService.withdraw(
          user.getAccountNumber(),
          transferDTO.getMoney()
    );
    userService.deposit(
          transferDTO.getAccountNumber(),
          transferDTO.getMoney()
    );

    return new TransactionDTO(
          transferDTO.getAccountNumber(),
          user.getAccountNumber(),
          transferDTO.getMoney()
    );
  }

  private void checkUserAndAccountTransferIsExist (
        User user,
        TransferDTO transferDTO
  ){
    User userReceived= userService.findUserByAccountNumber(transferDTO.getAccountNumber());
    if (user == null || userReceived == null) {
      log.error(
            MessageExceptionConstant.USER_OR_ACCOUNT_NUMBER_IS_NOT_EXIST);
      throw new AccountIsNotExistedException();
    }
  }

  private void checkMoneyInAccountIsEnough(
        User user,
        TransferDTO transferDTO
  ){
    if (user.getBalance() < transferDTO.getMoney()) {
      log.error(
            MessageExceptionConstant.ACCOUNT_IS_NOT_ENOUGH);
      throw new AmountIsNotEnoughException();
    }
  }


}
