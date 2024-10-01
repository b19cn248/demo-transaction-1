package com.opn.demo.service;
import com.opn.demo.dto.response.UserDetailDTO;
import com.opn.demo.entity.User;

import java.util.List;


public interface UserService {
  List<UserDetailDTO> getAllUser(int limit, int page);

  UserDetailDTO get(int id);


  void deleteById(int id);

  User save(
        String accountNumber,
        String email,
        String phoneNumber,
        double balance,
        int fullnameId,
        int accountId
  );

  User findUserById(int id);

  void updateUserByUserId(
        int userId, String email, String phoneNumber
  );

  void withdraw(String accountNumber, double amount);

  void deposit(String accountNumber, double amount);

  User findUserByAccountNumber(
        String accountNumberTransaction
  );

  void activeUser(String email);

  User findUserByEmail(String email);
}
