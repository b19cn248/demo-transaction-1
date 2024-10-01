package com.opn.demo.service.impl;



import com.opn.demo.dto.response.AddressDTO;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.entity.*;
import com.opn.demo.repository.UserRepository;
import com.opn.demo.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository usersRepository;

  @Override
  public List<UserDetailDTO> getAllUser(int limit, int page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    List<UserDetailDTO> results = usersRepository.getUserDetails(pageable).getContent();
    return aggregateUserDetails(results);
  }

  @Override
  public UserDetailDTO get(int id) {
    List<UserDetailDTO> results = usersRepository.getUserByIndexDetail(id);
    return aggregateAddressInUser(results);
  }


  @Override
  @Transactional
  public User save(
        String accountNumber,
        String email,
        String phoneNumber,
        double balance,
        int fullnameId,
        int accountId
  ) {
    log.info(
          "(save) accountNumber:{},email:{}," +
                "phoneNumber:{},balance:{}," +
                "fullnameId:{},accountId:{}",
          accountNumber, email,
          phoneNumber, balance,
          fullnameId, accountId
    );
    User user = new User(accountNumber, email
          , phoneNumber, balance,
          fullnameId, accountId
    );
    return usersRepository.save(user);
  }

  @Override
  public User findUserById(int id) {
    return usersRepository.findUserById(id);
  }

  @Override
  public void updateUserByUserId(int userId, String email, String phoneNumber) {
     usersRepository.updateUserByUserId(
          userId,
          email,
          phoneNumber
    );
  }

  @Override
  @Transactional
  public void withdraw(String accountNumber, double amount) {
    usersRepository.withdraw(accountNumber,amount);
  }

  @Override
  @Transactional
  public void deposit(String accountNumber, double amount) {
    usersRepository.deposit(accountNumber,amount);
  }

  @Override
  public User findUserByAccountNumber(String accountNumberTransaction) {
    return usersRepository.findUserByAccountNumber(accountNumberTransaction);
  }

  @Override
  @Transactional
  public void activeUser(String email) {
    usersRepository.activeUserByEmail(email);
  }

  @Override
  public User findUserByEmail(String email) {
    return usersRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public void deleteById(int id) {
    User user = usersRepository.findUserById(id);
    if (user == null) {
      log.error("this user is not exist");
      throw new RuntimeException();
    }
    usersRepository.setIsRemoveIsTrueById(id);
  }


  private List<UserDetailDTO> aggregateUserDetails(List<UserDetailDTO> results) {
    log.info("(aggregateUserDetails) results: {}", results);
    Map<Integer, UserDetailDTO> userDetailMap = new HashMap<>();
    for (UserDetailDTO result : results) {
      int userId = result.getId();
      AddressDTO addressDTO;
      if (result.getAddress() == null) {
        addressDTO = new AddressDTO();
      } else {
        addressDTO = result.getAddress().get(0);
      }
      if (userDetailMap.containsKey(userId)) {
        userDetailMap.get(userId).getAddress().add(addressDTO);
      } else {
        userDetailMap.put(
              userId,
              createNewUserDetailResponse(userId,result)
        );
      }

    }
    return new ArrayList<>(userDetailMap.values());
  }

  private UserDetailDTO createNewUserDetailResponse(
        int userId, UserDetailDTO result
  ) {
    log.info("(createNewUserDetailResponse) userId:{}", userId);
    return new UserDetailDTO(
          userId, result.getEmail(), result.getPhoneNumber(),
          result.getAccountNumber(), result.getBalance(),
          result.getFirstname(), result.getLastname(),
          result.getUsername(), result.getPass(),
          result.getAddress()
    );
  }

  private UserDetailDTO aggregateAddressInUser(List<UserDetailDTO> results) {
    log.info("(aggregateAddressInUser) results:{}",results);
    boolean firstElement = true;
    UserDetailDTO user = new UserDetailDTO();
    List<AddressDTO> aggregatedAddresses = new ArrayList<>();
    for (UserDetailDTO result : results) {

      if (result.getAddress().get(0) != null) {
        aggregatedAddresses.add(result.getAddress().get(0));
      }
      if (firstElement) {
        user = new UserDetailDTO(
              result.getId(), result.getEmail(),
              result.getPhoneNumber(), result.getAccountNumber(),
              result.getBalance(), result.getFirstname(),
              result.getLastname(), result.getUsername(),
              result.getPass()
        );
        firstElement = false;
      }
    }
    user.setAddress(aggregatedAddresses);
    return user;
  }
}
