package com.opn.demo.service;

import com.opn.demo.entity.Account;


public interface AccountService {
  Account save(String username, String pass);

  void update(
        int id,
        String username,
        String pass
  );

  Account findUserByUsername(String username);

}
