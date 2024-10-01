package com.opn.demo.controller;


import com.opn.demo.dto.request.ConfirmRequest;
import com.opn.demo.dto.request.ResendOtpRequest;
import com.opn.demo.dto.request.TransferDTO;
import com.opn.demo.dto.request.UserEditionDTO;
import com.opn.demo.dto.request.UserRequestDTO;
import com.opn.demo.dto.response.PageResponse;
import com.opn.demo.dto.response.TransactionDTO;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.facade.TransactionMoneyFacade;
import com.opn.demo.facade.UserManagementFacade;
import com.opn.demo.service.*;
import com.opn.demo.dto.response.ResponseGeneral;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.opn.demo.constant.MessageSuccessConstant;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {

  private final UserService usersService;

  private final UserManagementFacade userManagementFacade;

  private final TransactionMoneyFacade transactionMoneyFacade;

  private final EmailService emailService;


  @GetMapping
  public ResponseGeneral<PageResponse<UserDetailDTO>> list(
        @RequestParam(value = "limit", defaultValue = "10") int limit,
        @RequestParam(value = "page", defaultValue = "1") int page
  ) {
    log.info("(list) limit:{} page:{}", limit, page);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.GET_ALL_USER_SUCCESS,
          new PageResponse<>(
                usersService.getAllUser(limit, page),
                usersService.getAllUser(limit, page).size()
          )
    );
  }

  @GetMapping("/{id}")
  public ResponseGeneral<UserDetailDTO> detail(@PathVariable int id) {

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.GET_USER_BY_ID,
          usersService.get(id)
    );
  }

  @PostMapping
  public ResponseGeneral<Void> add(
        @RequestBody UserRequestDTO usersRequestDTO
  ) {

    userManagementFacade.add(usersRequestDTO);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.ADD_USER
    );
  }

  @PutMapping("/{id}")
  public ResponseGeneral<UserDetailDTO>update(
        @RequestBody UserEditionDTO userEditionDTO,
        @PathVariable int id
  ){
    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.EDIT_USER,
          userManagementFacade.update(userEditionDTO,id)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseGeneral<Void> delele(
        @PathVariable int id
  ) {

    usersService.deleteById(id);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.DELETE_USER
    );
  }

  @PostMapping("/{id}/transaction")
  public ResponseGeneral<TransactionDTO> transferMoney(
        @PathVariable int id,
        @RequestBody TransferDTO transferDTO
  ) {
    log.info("(transaction) id:{},transferDTO:{}",id,transferDTO);

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.TRANSACTION_MONEY,
          transactionMoneyFacade.transfer(id, transferDTO)
    );
  }

  @PostMapping("/verify")
  public ResponseGeneral<Void> confirm(
      @RequestBody ConfirmRequest confirmRequest
  ) {
    log.info("(confirm) confirmRequest:{}", confirmRequest);

    userManagementFacade.confirmEmail(confirmRequest);

    return ResponseGeneral.ofSuccess(
        MessageSuccessConstant.CONFIRM_INFORMATION_REGISTER,
        null
    );
  }

  @PostMapping("/resend-otp")
  public ResponseGeneral<Void> resendOtp(
      @RequestBody ResendOtpRequest resendOtpRequest
  ) {
    log.info("(confirm) resendOtpRequest:{}", resendOtpRequest);

    userManagementFacade.resendOtp(resendOtpRequest.getEmail());

    return ResponseGeneral.ofSuccess(
        MessageSuccessConstant.CONFIRM_INFORMATION_REGISTER,
        null
    );
  }


}