package com.opn.demo.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.opn.demo.dto.response.AddressDTO;
import jakarta.validation.constraints.NotNull;
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
public class UserEditionDTO {
  @NotNull
  private String email;
  @NotNull
  private String phoneNumber;
  @NotNull
  private String firstname;
  @NotNull
  private String lastname;
  @NotNull
  private String username;
  @NotNull
  private String pass;
  private List<AddressDTO> addressNew;
  private List<Integer> addressInDb;
}
