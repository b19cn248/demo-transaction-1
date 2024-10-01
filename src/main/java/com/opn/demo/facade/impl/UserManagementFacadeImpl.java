package com.opn.demo.facade.impl;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.dto.request.ConfirmRequest;
import com.opn.demo.dto.request.UserEditionDTO;
import com.opn.demo.dto.request.UserRequestDTO;
import com.opn.demo.dto.response.AddressDTO;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.entity.Account;
import com.opn.demo.entity.Address;
import com.opn.demo.entity.FullName;
import com.opn.demo.entity.User;
import com.opn.demo.exception.user.FieldInRequestIsEmptyException;
import com.opn.demo.exception.user.UserIsActiveException;
import com.opn.demo.exception.user.UserIsNotExistException;
import com.opn.demo.facade.UserManagementFacade;
import com.opn.demo.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserManagementFacadeImpl implements UserManagementFacade {
  private final UserService userService;

  private final AccountService accountService;

  private final FullNameService fullNameService;

  private final AddressUserService addressUserService;

  private final AddressService addressService;

  private final EmailService emailService;


  @Override
  @Transactional
  public void add(UserRequestDTO usersRequestDTO) {
    log.info("(add) usersRequestDTO:{}", usersRequestDTO);
    Account account = accountService.save(
          usersRequestDTO.getUsername(),
          usersRequestDTO.getPass()
    );
    FullName fullName = fullNameService.save(
          usersRequestDTO.getFirstname(),
          usersRequestDTO.getLastname()
    );


    User user = userService.save(
          usersRequestDTO.getAccountNumber(),
          usersRequestDTO.getEmail(),
          usersRequestDTO.getPhoneNumber(),
          usersRequestDTO.getBalance(),
          fullName.getId(),
          account.getId()
    );

    List<AddressDTO> newAddresses = usersRequestDTO.getAddressNew();
    List<Integer> existingAddressIds = usersRequestDTO.getAddressInDb();
    List<Address> addressNew = addressService.saveAll(newAddresses);

    for (Address address : addressNew) {
      existingAddressIds.add(address.getId());
    }
    addressUserService.saveListAddressUser(user.getId(), existingAddressIds);
    emailService.handleOtpRequest(
        user.getEmail()
    );
  }

  @Override
  @Transactional
  public UserDetailDTO update(UserEditionDTO userEditionDTO, int id) {
    log.info("(update) id:{}", id);

    if (userService.findUserById(id) == null) {
      log.error(MessageExceptionConstant.USER_IS_NOT_EXIST);
      throw new UserIsNotExistException();
    }
    UserDetailDTO userDetailByIndex = userService.get(id);
    this.validateUserEditionDTO(userEditionDTO);

    this.updateAccountAndFullnameAndUser(userEditionDTO, userDetailByIndex, id);

    this.updateAddress(
          id,
          userEditionDTO.getAddressNew(),
          userEditionDTO.getAddressInDb()
    );

    return userService.get(id);
  }

  @Override
  @Transactional
  public void confirmEmail(ConfirmRequest confirmRequest) {
    String otpInCache =(String)  emailService.getValue(confirmRequest.getEmail());
    if(otpInCache.equals(confirmRequest.getOtp())){
      userService.activeUser(confirmRequest.getEmail());
    }
  }

  @Override
  @Transactional
  public void resendOtp(String email) {
    User user  =userService.findUserByEmail(email);

    if(user==null||user.isRemove()){
      throw new UserIsNotExistException();
    }
    else {
      if (user.isActive()){
        throw new UserIsActiveException();
      }

      emailService.handleOtpRequest(email);
    }

  }

  private void updateAccountAndFullnameAndUser(
        UserEditionDTO userEditionDTO,
        UserDetailDTO userDetailByIndex,
        int userId
  ) {

    User userByIndex = userService.findUserById(userId);

    accountService.update(
          userByIndex.getAccountId(),
          userEditionDTO.getUsername(),
          userEditionDTO.getPass()
    );

    if (
          !userEditionDTO.getFirstname().equals(userDetailByIndex.getFirstname()) ||
                !userEditionDTO.getLastname().equals(userDetailByIndex.getLastname())
    ) {
      fullNameService.updateByIndex(
            userByIndex.getFullnameId(),
            userEditionDTO.getFirstname(),
            userEditionDTO.getLastname()
      );
    }

    if (
          userDetailByIndex.getEmail().equals(userEditionDTO.getEmail()) ||
                userEditionDTO.getPhoneNumber().equals(userDetailByIndex.getPhoneNumber())
    ) {
      userService.updateUserByUserId(
            userId,
            userEditionDTO.getEmail(),
            userEditionDTO.getPhoneNumber()
      );
    }

  }

  private void updateAddress(
        int userId,
        List<AddressDTO> newAddresses,
        List<Integer> existingAddressIds
  ) {
    log.info("(updateAddress) userId:{},newAddresses:{}," +
                "existingAddressIds:{}",
          userId,
          newAddresses,
          existingAddressIds);

    List<Address> addressNew = addressService.saveAll(newAddresses);

    List<Integer> listAddressHaveExistInUser = addressUserService.getListAddressIdByUserId(userId);

    this.updateAddressUser(
          addressNew,
          listAddressHaveExistInUser,
          existingAddressIds,
          userId
    );
  }

  private void updateAddressUser(
        List<Address> addressNew,
        List<Integer> listAddressHaveExistInUser,
        List<Integer> existingAddressIds,
        int userId
  ){
    List<Integer> listAddressIdAddInUserId = new ArrayList<>();
    for (int addressIdInExiting : existingAddressIds) {
      if (!listAddressHaveExistInUser.contains(addressIdInExiting)) {
        listAddressIdAddInUserId.add(addressIdInExiting);
      }
    }

    List<Integer> listAddressIdWantToRemove = new ArrayList<>();
    for (int addressHaveExistInUser : listAddressHaveExistInUser) {
      if (!existingAddressIds.contains(addressHaveExistInUser)) {
        listAddressIdWantToRemove.add(addressHaveExistInUser);
      }
    }

    if (!listAddressIdWantToRemove.isEmpty()) {
      addressUserService.deleteByUserIdAndListAddressIds(userId, listAddressIdWantToRemove);
    }

    if (!addressNew.isEmpty()) {
      for (Address address : addressNew) {
        listAddressIdAddInUserId.add(address.getId());
      }
    }

    addressUserService.saveListAddressUser(userId, listAddressIdAddInUserId);
  }

  private void validateUserEditionDTO(UserEditionDTO userEditionDTO) {
    if (
          userEditionDTO.getEmail().isEmpty() ||
          userEditionDTO.getPhoneNumber().isEmpty() ||
          userEditionDTO.getFirstname().isEmpty() ||
          userEditionDTO.getLastname().isEmpty()
    ) {
      log.error(MessageExceptionConstant.
            FIELD_IN_USER_EDITION_IS_EMPTY);
      throw new FieldInRequestIsEmptyException();
    }
  }

}