package com.opn.demo.facade;

import com.opn.demo.dto.request.ConfirmRequest;
import com.opn.demo.dto.request.UserEditionDTO;
import com.opn.demo.dto.request.UserRequestDTO;
import com.opn.demo.dto.response.UserDetailDTO;

public interface UserManagementFacade {
  void add(UserRequestDTO usersRequestDTO);

  UserDetailDTO update(UserEditionDTO userEditionDTO, int id);

  void confirmEmail(ConfirmRequest confirmRequest);

  void resendOtp(String email);
}
