package com.opn.demo.service.impl;

import com.opn.demo.entity.Account;
import com.opn.demo.entity.Principal;
import com.opn.demo.exception.account.AccountNotFoundException;
import com.opn.demo.service.AccountService;
import com.opn.demo.service.PrincipalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalServiceImpl implements PrincipalService {

  private final AccountService accountService;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountService.findUserByUsername(username);

    if (account == null) {
      throw new AccountNotFoundException();
    }
    return new Principal(account);
  }
}
