package com.opn.demo.service;

import com.opn.demo.dto.request.LoginRequest;
import com.opn.demo.dto.response.LoginResponse;

public interface AuthService {
  LoginResponse login(LoginRequest loginRequest);
}
