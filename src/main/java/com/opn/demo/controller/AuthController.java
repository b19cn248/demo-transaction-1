package com.opn.demo.controller;

import com.opn.demo.constant.MessageSuccessConstant;
import com.opn.demo.dto.request.LoginRequest;
import com.opn.demo.dto.response.LoginResponse;
import com.opn.demo.dto.response.ResponseGeneral;
import com.opn.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseGeneral<LoginResponse> login(@RequestBody LoginRequest loginRequest){

    return ResponseGeneral.ofSuccess(
          MessageSuccessConstant.LOGIN,
          this.authService.login(loginRequest)
    );
  }

  @GetMapping
  public String get(){
    return "ok";
  }
}
