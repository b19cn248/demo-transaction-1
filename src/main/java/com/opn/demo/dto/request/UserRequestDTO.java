package com.opn.demo.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.opn.demo.dto.response.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequestDTO {
  private String email;
  private String phoneNumber;
  private String accountNumber;
  private double balance;
  private String firstname;
  private String lastname;
  private String username;
  private String pass;
  private List<AddressDTO> addressNew;
  private List<Integer> addressInDb;
}
