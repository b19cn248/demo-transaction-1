package com.opn.demo.service.impl;

import com.opn.demo.entity.Account;
import com.opn.demo.repository.AccountRepository;
import com.opn.demo.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public Account save(String username, String pass) {
    Account account = new Account(username, passwordEncoder.encode(pass));
    return accountRepository.save(account);
  }

  @Override
  @Transactional
  public void update(
        int id,
        String username,
        String pass
  ) {
    log.info("(update) userId:{},username:{}", id, username);
    accountRepository.update(id, username, passwordEncoder.encode(pass));
  }

  @Override
  public Account findUserByUsername(String username) {
    return accountRepository.findAccountByUsername(username);
  }



}
