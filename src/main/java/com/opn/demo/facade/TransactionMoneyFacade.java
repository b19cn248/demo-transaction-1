package com.opn.demo.facade;

import com.opn.demo.dto.request.TransferDTO;
import com.opn.demo.dto.response.TransactionDTO;

public interface TransactionMoneyFacade {
  TransactionDTO transfer(int id, TransferDTO transferDTO);
}
