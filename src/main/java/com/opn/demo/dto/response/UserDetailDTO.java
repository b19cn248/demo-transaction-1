package com.opn.demo.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDetailDTO {
  private int id;
  private String email;
  private String phoneNumber;
  private String accountNumber;
  private double balance;
  private String firstname;
  private String lastname;
  private String username;
  private String pass;
  private List<AddressDTO>  address;

  public UserDetailDTO(
        int id,
        String email,
        String phoneNumber,
        String accountNumber,
        double balance,
        String firstname,
        String lastname,
        String username,
        String pass
  ) {
    this.id = id;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.pass = pass;
  }

  public UserDetailDTO(
        int id,
        String email,
        String phoneNumber,
        String accountNumber,
        double balance,
        String firstname,
        String lastname,
        String username,
        String pass,
        String province,
        String district,
        String ward
  ) {
    this.id = id;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.pass = pass;
    AddressDTO addressDTO = new AddressDTO(province, district, ward);
    List<AddressDTO> listAddress = new ArrayList<>();
    listAddress.add(addressDTO);
    this.address = listAddress;
  }
}

