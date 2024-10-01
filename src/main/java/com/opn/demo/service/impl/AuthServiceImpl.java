package com.opn.demo.service.impl;

import com.opn.demo.dto.request.LoginRequest;
import com.opn.demo.dto.response.LoginResponse;
import com.opn.demo.service.AuthService;
import com.opn.demo.util.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  @Transactional
  public LoginResponse login(LoginRequest loginRequest) {
    try {

      Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(),
                  loginRequest.getPassword()
            )
      );

      String jwt = jwtTokenProvider.generateToken(authentication);

      return new LoginResponse(jwt);

    } catch (RuntimeException ex) {
      ex.printStackTrace();
      throw new RuntimeException();
    }


  }
}
